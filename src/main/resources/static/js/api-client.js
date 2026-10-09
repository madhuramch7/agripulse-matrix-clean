/**
 * AgriPulse Secure HTTP Client
 * Ensures authentication credentials, CSRF tokens, and proper headers on every request.
 */
const ApiClient = (() => {
    function getCsrfToken() {
        const match = document.cookie.match(new RegExp('(^| )XSRF-TOKEN=([^;]+)'));
        return match ? decodeURIComponent(match[2]) : null;
    }

    async function request(endpoint, options = {}) {
        const defaultHeaders = {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        };

        const csrfToken = getCsrfToken();
        if (csrfToken) {
            defaultHeaders['X-XSRF-TOKEN'] = csrfToken;
        }

        const config = {
            ...options,
            credentials: 'include',
            headers: {
                ...defaultHeaders,
                ...(options.headers || {})
            }
        };

        try {
            const response = await fetch(endpoint, config);

            if (response.status === 401) {
                console.warn('[ApiClient] 401 Unauthorized encountered. Redirecting to auth flow.');
                window.location.href = 'index.html?error=session_expired';
                return null;
            }

            if (!response.ok) {
                const errorBody = await response.json().catch(() => ({}));
                throw new Error(errorBody.message || `HTTP ${response.status}: ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error(`[ApiClient] Request failure at ${endpoint}:`, error);
            throw error;
        }
    }

    return {
        get: (url, headers = {}) => request(url, { method: 'GET', headers }),
        post: (url, body, headers = {}) => request(url, { method: 'POST', body: JSON.stringify(body), headers }),
        put: (url, body, headers = {}) => request(url, { method: 'PUT', body: JSON.stringify(body), headers }),
        delete: (url, headers = {}) => request(url, { method: 'DELETE', headers })
    };
})();