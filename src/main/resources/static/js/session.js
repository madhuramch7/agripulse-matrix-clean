// AgriPulse Matrix v3.0 - Global Session Management Script

document.addEventListener("DOMContentLoaded", async () => {
    const currentPath = window.location.pathname;
    const isPublicPage = currentPath.includes("welcome.html") || 
                         currentPath.includes("login.html") || 
                         currentPath.includes("register.html") || 
                         currentPath === "/" ||
                         currentPath === "";

    try {
        const response = await fetch("/api/auth/me", {
            method: "GET",
            credentials: "include"
        });

        if (response.ok) {
            const user = await response.json();
            
            // If logged in and on a public page, redirect to dashboard or onboarding
            if (isPublicPage) {
                if (user.onboardingComplete) {
                    window.location.href = "/dashboard.html";
                } else {
                    window.location.href = "/onboarding.html";
                }
                return;
            }

            // If onboarding is not complete and user is not on onboarding page
            if (!user.onboardingComplete && !currentPath.includes("onboarding.html")) {
                window.location.href = "/onboarding.html";
                return;
            }

            // Update user email display elements if present
            const userEmailEls = document.querySelectorAll(".user-email-display");
            userEmailEls.forEach(el => el.textContent = user.email);

        } else {
            // Not authenticated
            if (!isPublicPage) {
                window.location.href = "/welcome.html";
            }
        }
    } catch (error) {
        console.error("Session check failed:", error);
        if (!isPublicPage) {
            window.location.href = "/welcome.html";
        }
    }
});

// Global Logout Utility
async function logoutUser() {
    try {
        await fetch("/api/auth/logout", {
            method: "POST",
            credentials: "include"
        });
        window.location.href = "/welcome.html";
    } catch (e) {
        console.error("Logout failed", e);
        window.location.href = "/welcome.html";
    }
}