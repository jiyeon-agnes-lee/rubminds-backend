package com.rubminds.api.team.web;


import com.rubminds.MvcTest;
import com.rubminds.api.post.domain.*;
import com.rubminds.api.skill.domain.CustomSkill;
import com.rubminds.api.skill.domain.Skill;
import com.rubminds.api.team.Service.TeamService;
import com.rubminds.api.team.domain.Team;
import com.rubminds.api.team.domain.TeamUser;
import com.rubminds.api.team.dto.TeamResponse;
import com.rubminds.api.user.domain.SignupProvider;
import com.rubminds.api.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Team API 문서화")
@WebMvcTest(TeamController.class)
public class TeamControllerTest extends MvcTest {

    @MockBean
    private TeamService teamService;
    private User user1;
    private User user2;
    private TeamUser user3;
    private Post post1;
    private Team team;
    private List<TeamUser> userList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        user1 = User.builder()
                .id(1L)
                .oauthId("1")
                .nickname("동그라미")
                .job("학생")
                .introduce("안녕하세요!")
                .provider(SignupProvider.RUBMINDS)
                .signupCheck(true)
                .build();

        user2 = User.builder()
                .id(2L)
                .oauthId("2")
                .nickname("동그라미")
                .job("학생")
                .introduce("안녕하세요!")
                .provider(SignupProvider.RUBMINDS)
                .signupCheck(true)
                .build();

        team = Team.builder()
                .id(1L)
            .admin(user1)
            .teamUsers(userList)
            .build();

        user3 = TeamUser.builder()
                .id(2L)
                .team(team)
                .finish(false)
                .user(user2)
                .build();

        userList.add(user3);


        post1 = Post.builder()
                .id(1L)
                .title("테스트")
                .content("내용")
                .region("서울")
                .postStatus(PostStatus.RECRUIT)
                .kinds(Kinds.PROJECT)
                .headcount(3)
                .meeting(Meeting.BOTH)
                .writer(user1)
                .postSkills(Collections.singletonList(PostSkill.builder().id(1L).skill(Skill.builder().id(1L).name("JAVA").build()).build()))
                .customSkills(Collections.singletonList(CustomSkill.builder().id(1L).name("java").build()))
                .team(Team.builder().id(1L).admin(user1).build())
                .postFileList(Collections.singleton(PostFile.builder().id(1L).url("file url").build()))
                .build();



    }

    @Test
    @DisplayName("팀 조회 문서화")
    public void getTeam() throws Exception {
        TeamResponse.GetTeam response = TeamResponse.GetTeam.build(team,userList);
        given(teamService.getTeamInfo(any())).willReturn(response);

        ResultActions results = mvc.perform(RestDocumentationRequestBuilders.get("/api/team/{teamId}", 1L));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("team_info", pathParameters(
                        parameterWithName("teamId").description("팀 식별자")
                        ),
                        responseFields(
                                fieldWithPath("teamId").type(JsonFieldType.NUMBER).description("팀 식별자"),
                                fieldWithPath("adminId").type(JsonFieldType.NUMBER).description("팀장 식별자"),
                                fieldWithPath("teamUsers").type(JsonFieldType.ARRAY).description("유저 식별자"),
                                fieldWithPath("teamUsers[].teamUserId").type(JsonFieldType.NUMBER).description("팀원 식별자"),
                                fieldWithPath("teamUsers[].userId").type(JsonFieldType.NUMBER).description("팀원의 유저아이디 식별자"),
                                fieldWithPath("teamUsers[].userNickname").type(JsonFieldType.STRING).description("팀원의 닉네임"),
                                fieldWithPath("teamUsers[].finish").type(JsonFieldType.BOOLEAN).description("팀원의 종료버튼 승인여부")
                        )
                ));
    }
}