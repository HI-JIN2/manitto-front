package party.manitto.ui

import org.jetbrains.compose.web.css.*

object AppStyles : StyleSheet() {
    
    val container by style {
        backgroundColor(Color.white)
        borderRadius(20.px)
        padding(40.px)
        property("box-shadow", "0 10px 40px rgba(0,0,0,0.2)")
        minWidth(350.px)
        maxWidth(450.px)
        textAlign("center")
    }
    
    val title by style {
        fontSize(28.px)
        fontWeight(700)
        color(Color("#333"))
        marginBottom(30.px)
    }
    
    val input by style {
        width(100.percent)
        padding(15.px)
        fontSize(16.px)
        border(1.px, LineStyle.Solid, Color("#ddd"))
        borderRadius(10.px)
        marginBottom(15.px)
        property("outline", "none")
        property("transition", "border-color 0.3s")
        
        self + focus style {
            borderColor(Color("#667eea"))
        }
    }
    
    val button by style {
        width(100.percent)
        padding(15.px)
        fontSize(16.px)
        fontWeight(600)
        color(Color.white)
        backgroundColor(Color("#667eea"))
        border(0.px)
        borderRadius(10.px)
        cursor("pointer")
        property("transition", "transform 0.2s, background-color 0.3s")
        
        self + hover style {
            backgroundColor(Color("#5a6fd6"))
            property("transform", "translateY(-2px)")
        }
        
        self + active style {
            property("transform", "translateY(0)")
        }
    }
    
    val secondaryButton by style {
        width(100.percent)
        padding(15.px)
        fontSize(16.px)
        fontWeight(600)
        color(Color("#667eea"))
        backgroundColor(Color.transparent)
        border(2.px, LineStyle.Solid, Color("#667eea"))
        borderRadius(10.px)
        cursor("pointer")
        marginTop(10.px)
        property("transition", "all 0.3s")
        
        self + hover style {
            backgroundColor(Color("#667eea"))
            color(Color.white)
        }
    }
    
    val participantList by style {
        listStyleType("none")
        padding(0.px)
        marginTop(20.px)
        textAlign("left")
    }
    
    val participantItem by style {
        padding(12.px, 15.px)
        backgroundColor(Color("#f8f9fa"))
        borderRadius(8.px)
        marginBottom(8.px)
        fontSize(14.px)
        color(Color("#555"))
    }
    
    val successMessage by style {
        color(Color("#28a745"))
        fontWeight(600)
        marginTop(20.px)
        padding(15.px)
        backgroundColor(Color("#d4edda"))
        borderRadius(10.px)
    }
    
    val errorMessage by style {
        color(Color("#dc3545"))
        fontWeight(600)
        marginTop(20.px)
        padding(15.px)
        backgroundColor(Color("#f8d7da"))
        borderRadius(10.px)
    }
    
    val resultBox by style {
        marginTop(30.px)
        padding(25.px)
        backgroundColor(Color("#fff3cd"))
        borderRadius(15.px)
        fontSize(24.px)
        fontWeight(700)
        color(Color("#856404"))
    }
    
    val navLink by style {
        color(Color("#667eea"))
        textDecoration("none")
        fontSize(14.px)
        marginTop(20.px)
        display(DisplayStyle.InlineBlock)
        
        self + hover style {
            textDecoration("underline")
        }
    }
    
    val loadingSpinner by style {
        fontSize(24.px)
        property("animation", "spin 1s linear infinite")
    }
}

