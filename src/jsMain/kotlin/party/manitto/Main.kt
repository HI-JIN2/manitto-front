package party.manitto

import androidx.compose.runtime.*
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import party.manitto.auth.AuthState
import party.manitto.ui.*

fun main() {
    // 인증 상태 초기화
    AuthState.init()
    
    renderComposable(rootElementId = "root") {
        Style(AppStyles)
        App()
    }
}

@Composable
fun App() {
    var currentPath by remember { mutableStateOf(window.location.hash.removePrefix("#")) }
    
    // URL 변경 감지
    DisposableEffect(Unit) {
        val listener: (dynamic) -> Unit = {
            currentPath = window.location.hash.removePrefix("#")
        }
        window.addEventListener("hashchange", listener)
        
        onDispose {
            window.removeEventListener("hashchange", listener)
        }
    }
    
    val navigate: (String) -> Unit = { path ->
        window.location.hash = "#$path"
        currentPath = path
    }
    
    // 로그인 체크
    if (AuthState.isLoading) {
        // 로딩 중
        return
    }
    
    if (AuthState.user == null) {
        LoginPage()
        return
    }
    
    // 라우팅
    when {
        currentPath.isEmpty() || currentPath == "/" -> {
            CreatePartyPage(onNavigate = navigate)
        }
        currentPath.matches(Regex("/party/(\\d+)/join")) -> {
            val partyId = currentPath.substringAfter("/party/").substringBefore("/join")
            JoinPartyPage(partyId = partyId, onNavigate = navigate)
        }
        currentPath.matches(Regex("/party/(\\d+)/status")) -> {
            val partyId = currentPath.substringAfter("/party/").substringBefore("/status")
            PartyStatusPage(partyId = partyId, onNavigate = navigate)
        }
        currentPath.matches(Regex("/party/(\\d+)/result")) -> {
            val partyId = currentPath.substringAfter("/party/").substringBefore("/result")
            MatchResultPage(partyId = partyId, onNavigate = navigate)
        }
        else -> {
            // 404 - 홈으로 리다이렉트
            CreatePartyPage(onNavigate = navigate)
        }
    }
}

