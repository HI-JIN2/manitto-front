package party.manitto.ui

import androidx.compose.runtime.*
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.*
import party.manitto.api.ApiClient
import party.manitto.api.Participant
import party.manitto.api.PartyStatus

@Composable
fun PartyStatusPage(partyId: String, onNavigate: (String) -> Unit) {
    var participants by remember { mutableStateOf<List<Participant>>(emptyList()) }
    var isMatched by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var isMatching by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }
    
    val scope = MainScope()
    
    // 데이터 로드
    LaunchedEffect(partyId) {
        try {
            participants = ApiClient.get("/parties/$partyId/participants")
            val status: PartyStatus = ApiClient.get("/parties/$partyId/status")
            isMatched = status.matched
        } catch (e: Exception) {
            console.log("Error: ${e.message}")
            message = "파티 정보를 불러올 수 없습니다 😢"
        } finally {
            isLoading = false
        }
    }
    
    Div({ classes(AppStyles.container) }) {
        H1({ classes(AppStyles.title) }) {
            Text("👥 파티 참가자 목록")
        }
        
        if (isLoading) {
            Div({ classes(AppStyles.loadingSpinner) }) {
                Text("⏳")
            }
        } else {
            // 초대 링크 복사 버튼
            Button({
                classes(AppStyles.secondaryButton)
                onClick {
                    val link = "${window.location.origin}/#/party/$partyId/join"
                    window.navigator.clipboard.writeText(link)
                    window.alert("초대 링크가 복사되었습니다! 📋")
                }
            }) {
                Text("초대 링크 복사 📋")
            }
            
            // 참가자 목록
            if (participants.isEmpty()) {
                P { Text("아직 참가자가 없습니다.") }
            } else {
                Ul({ classes(AppStyles.participantList) }) {
                    participants.forEach { participant ->
                        Li({ classes(AppStyles.participantItem) }) {
                            Text("👤 ${participant.email}")
                        }
                    }
                }
            }
            
            // 매칭 상태
            if (isMatched) {
                Div({ classes(AppStyles.successMessage) }) {
                    Text("🎁 이미 매칭이 완료된 파티입니다!")
                }
                
                Button({
                    classes(AppStyles.button)
                    onClick { onNavigate("/party/$partyId/result") }
                }) {
                    Text("내 마니또 확인하기")
                }
            } else {
                Button({
                    classes(AppStyles.button)
                    if (isMatching) attr("disabled", "true")
                    onClick {
                        isMatching = true
                        scope.launch {
                            try {
                                ApiClient.postEmpty<Unit>("/parties/$partyId/match")
                                message = "매칭 완료! 이메일이 발송되었습니다 ✉️"
                                isMatched = true
                            } catch (e: Exception) {
                                console.log("Error: ${e.message}")
                                message = "매칭 실패 😢"
                            } finally {
                                isMatching = false
                            }
                        }
                    }
                }) {
                    Text(if (isMatching) "매칭 중..." else "매칭 시작 🎁")
                }
            }
            
            message?.let { msg ->
                Div({
                    classes(if (msg.contains("완료")) AppStyles.successMessage else AppStyles.errorMessage)
                }) {
                    Text(msg)
                }
            }
        }
        
        A(href = "#/") {
            classes(AppStyles.navLink)
            Text("← 홈으로")
        }
    }
}

