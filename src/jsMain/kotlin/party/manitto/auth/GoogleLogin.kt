package party.manitto.auth

import androidx.compose.runtime.*
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLDivElement

@Composable
fun GoogleLoginButton() {
    var buttonRendered by remember { mutableStateOf(false) }
    
    DisposableEffect(Unit) {
        val clientId = js("window.ENV?.GOOGLE_CLIENT_ID") as? String 
            ?: "772988401705-4io9tviphr75k075kb9lbrnmn960h2r8.apps.googleusercontent.com"
        
        // Google Sign-In 초기화
        js("""
            if (window.google && window.google.accounts) {
                window.google.accounts.id.initialize({
                    client_id: clientId,
                    callback: function(response) {
                        window.handleGoogleLogin(response.credential);
                    }
                });
            }
        """)
        
        // Kotlin callback 등록
        window.asDynamic().handleGoogleLogin = { credential: String ->
            AuthState.loginWithGoogle(credential)
        }
        
        onDispose {
            window.asDynamic().handleGoogleLogin = null
        }
    }
    
    Div({
        attr("id", "google-login-btn")
        style {
            property("margin-top", "20px")
        }
    }) {
        DisposableEffect(Unit) {
            // 버튼 렌더링
            js("""
                setTimeout(function() {
                    if (window.google && window.google.accounts) {
                        window.google.accounts.id.renderButton(
                            document.getElementById('google-login-btn'),
                            { 
                                theme: 'outline', 
                                size: 'large',
                                text: 'signin_with',
                                shape: 'rectangular'
                            }
                        );
                    }
                }, 100);
            """)
            
            onDispose { }
        }
    }
}

