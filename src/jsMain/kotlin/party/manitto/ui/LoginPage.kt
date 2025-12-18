package party.manitto.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*
import party.manitto.auth.GoogleLoginButton

@Composable
fun LoginPage() {
    Div({ classes(AppStyles.container) }) {
        H1({ classes(AppStyles.title) }) {
            Text("🎁 마니또에 오신 것을 환영합니다!")
        }
        
        P({
            style {
                property("color", "#666")
                property("margin-bottom", "30px")
            }
        }) {
            Text("Google 계정으로 로그인하여 마니또 파티를 시작하세요")
        }
        
        GoogleLoginButton()
    }
}

