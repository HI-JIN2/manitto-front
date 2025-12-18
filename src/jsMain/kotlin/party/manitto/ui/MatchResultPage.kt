package party.manitto.ui

import androidx.compose.runtime.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.*
import party.manitto.api.ApiClient
import party.manitto.api.MatchResult

@Composable
fun MatchResultPage(partyId: String, onNavigate: (String) -> Unit) {
    var result by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    
    val scope = MainScope()
    
    Div({ classes(AppStyles.container) }) {
        H1({ classes(AppStyles.title) }) {
            Text("🎁 당신의 마니또는...")
        }
        
        if (result != null) {
            Div({ classes(AppStyles.resultBox) }) {
                Text("🎉 $result 🎉")
            }
            
            P({
                style {
                    property("margin-top", "20px")
                    property("color", "#666")
                    property("font-size", "14px")
                }
            }) {
                Text("이 사람에게 몰래 선물을 준비해보세요!")
            }
        } else {
            Button({
                classes(AppStyles.button)
                if (isLoading) attr("disabled", "true")
                onClick {
                    isLoading = true
                    error = null
                    scope.launch {
                        try {
                            val response: MatchResult = ApiClient.get("/parties/match/result")
                            result = response.receiver
                        } catch (e: Exception) {
                            console.log("Error: ${e.message}")
                            error = "결과를 불러올 수 없습니다 😢"
                        } finally {
                            isLoading = false
                        }
                    }
                }
            }) {
                Text(if (isLoading) "확인 중..." else "결과 보기 👀")
            }
        }
        
        error?.let {
            Div({ classes(AppStyles.errorMessage) }) {
                Text(it)
            }
        }
        
        A(href = "#/party/$partyId/status") {
            classes(AppStyles.navLink)
            Text("← 파티 상태로")
        }
    }
}

