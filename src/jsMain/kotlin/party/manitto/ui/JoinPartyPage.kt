package party.manitto.ui

import androidx.compose.runtime.*
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.*
import party.manitto.api.ApiClient
import party.manitto.api.JoinPartyRequest
import party.manitto.auth.AuthState

@Composable
fun JoinPartyPage(partyId: String, onNavigate: (String) -> Unit) {
    var isLoading by remember { mutableStateOf(false) }
    var joined by remember { mutableStateOf(false) }
    
    val scope = MainScope()
    val user = AuthState.user
    
    Div({ classes(AppStyles.container) }) {
        H1({ classes(AppStyles.title) }) {
            Text("🔑 마니또 방 참여")
        }
        
        P {
            Text("파티 ID: $partyId")
        }
        
        if (joined) {
            Div({ classes(AppStyles.successMessage) }) {
                Text("🎈 참여 완료!")
            }
            
            Button({
                classes(AppStyles.button)
                onClick { onNavigate("/party/$partyId/status") }
            }) {
                Text("파티 상태 보기")
            }
        } else {
            Button({
                classes(AppStyles.button)
                if (isLoading) attr("disabled", "true")
                onClick {
                    if (user == null) {
                        window.alert("로그인이 필요합니다 😢")
                        return@onClick
                    }
                    
                    isLoading = true
                    scope.launch {
                        try {
                            ApiClient.post<JoinPartyRequest, Unit>(
                                "/parties/$partyId/join",
                                JoinPartyRequest(user.sub)
                            )
                            joined = true
                        } catch (e: Exception) {
                            console.log("Error: ${e.message}")
                            window.alert("참여 실패 😢")
                        } finally {
                            isLoading = false
                        }
                    }
                }
            }) {
                Text(if (isLoading) "참여 중..." else "참여하기 🎈")
            }
        }
        
        A(href = "#/") {
            classes(AppStyles.navLink)
            Text("← 홈으로")
        }
    }
}

