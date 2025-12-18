package party.manitto.ui

import androidx.compose.runtime.*
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.*
import party.manitto.api.ApiClient
import party.manitto.api.CreatePartyRequest
import party.manitto.api.PartyResponse

@Composable
fun CreatePartyPage(onNavigate: (String) -> Unit) {
    var partyName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    val scope = MainScope()
    
    Div({ classes(AppStyles.container) }) {
        H1({ classes(AppStyles.title) }) {
            Text("🎉 마니또 방 만들기")
        }
        
        Form({
            onSubmit { event ->
                event.preventDefault()
                if (partyName.isBlank() || password.isBlank()) {
                    window.alert("방 이름과 비밀번호를 입력해주세요!")
                    return@onSubmit
                }
                
                isLoading = true
                scope.launch {
                    try {
                        val response = ApiClient.post<CreatePartyRequest, PartyResponse>(
                            "/parties",
                            CreatePartyRequest(partyName, password)
                        )
                        window.alert("파티 생성 완료! ID: ${response.id}")
                        onNavigate("/party/${response.id}/status")
                    } catch (e: Exception) {
                        console.log("Error: ${e.message}")
                        window.alert("파티 생성 실패 😢")
                    } finally {
                        isLoading = false
                    }
                }
            }
        }) {
            Input(InputType.Text) {
                classes(AppStyles.input)
                placeholder("방 이름")
                value(partyName)
                onInput { partyName = it.value }
            }
            
            Input(InputType.Password) {
                classes(AppStyles.input)
                placeholder("비밀번호")
                value(password)
                onInput { password = it.value }
            }
            
            Button({
                classes(AppStyles.button)
                attr("type", "submit")
                if (isLoading) attr("disabled", "true")
            }) {
                Text(if (isLoading) "생성 중..." else "방 만들기 ✨")
            }
        }
    }
}

