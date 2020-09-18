package se.newton.tash.restapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.model.Token;
import se.newton.tash.restapi.repository.TokenRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TokenServiceImplTest {
  @InjectMocks
  TokenServiceImpl tokenService;

  @Mock
  TokenRepository tokenRepository;

  TashUser user, admin;
  Token userToken, adminToken;
  String userTokenString, adminTokenString;

  @BeforeEach
  public void setUp() {
    TashUser.TashUserBuilder userBuilder = TashUser.builder()
        .id(10L).longitude(0.1).latitude(0.1)
        .firstName("Hampus").lastName("Hermansson")
        .email("hampus.hermansson@niclas.no")
        .avatarUrl("https://lollerstorm.se/hahiheho.png");
    user = userBuilder.admin(false).build();
    admin = userBuilder.admin(true).build();
    
    Token.TokenBuilder tokenBuilder = Token.builder();
    userTokenString = "HELLO-IAM-ACOOL-USER";
    adminTokenString = "HITHERE-MISTER-ADMIN-GOZAIMASU";
    userToken = tokenBuilder.admin(false).user(user).token(userTokenString).build();
    adminToken = tokenBuilder.admin(true).user(admin).token(adminTokenString).build();
    when(tokenRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
    when(tokenRepository.findById(eq(userTokenString))).thenReturn(Optional.of(userToken));
    when(tokenRepository.findById(eq(adminTokenString))).thenReturn(Optional.of(adminToken));
  }

  @Test
  public void createTokenOrNull_whenUserIsNormalUser_returnsValidToken() {
    Token reply = tokenService.createTokenOrNull(user);
    
    ArgumentCaptor<Token> tokenCaptor = ArgumentCaptor.forClass(Token.class);
    verify(tokenRepository, times(1))
        .save(tokenCaptor.capture());
    Token token = tokenCaptor.getValue();

    assertThat(token).isEqualTo(reply);
    assertThat(token.getAdmin()).isEqualTo(user.getAdmin());
    assertThat(token.getToken()).isNotNull();
    assertThat(token.getUser()).isEqualTo(user);
  }

  @Test
  public void createTokenOrNull_whenUserNull_returnsNull() {
    Token token = tokenService.createTokenOrNull(null);
    assertThat(token).isNull();
  }

  @Test
  public void createTokenOrException_whenUserIsAdmin_returnsValidToken() {
    Token reply = tokenService.createTokenOrNull(admin);

    ArgumentCaptor<Token> tokenCaptor = ArgumentCaptor.forClass(Token.class);
    verify(tokenRepository, times(1))
        .save(tokenCaptor.capture());
    Token token = tokenCaptor.getValue();

    assertThat(token).isEqualTo(reply);
    assertThat(token.getAdmin()).isEqualTo(admin.getAdmin());
    assertThat(token.getToken()).isNotNull();
    assertThat(token.getUser()).isEqualTo(admin);
  }

  @Test
  public void createTokenOrException_whenUserNull_throwsException() {
    BadCredentialsException bce = Assertions.assertThrows(
        BadCredentialsException.class,
        () -> tokenService.createTokenOrException(null)
    );
    assertThat(bce.getMessage()).isEqualTo("Invalid token");
  }
}
