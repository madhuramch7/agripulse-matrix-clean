/**
 * AgriPulse Matrix Dashboard - Centralized API Client Module
 * Handles asynchronous fetch calls, JSON parsing, dynamic error handling, and standard headers.
 */

const ApiClient = {
    // Base URL for Spring Boot REST API Endpoints
    baseUrl: '/api',

    /**
     * Generic HTTP Request Wrapper
     * @param {string} endpoint - API route (e.g., '/auth/login', '/farmer/profile')
     * @param {string} method - HTTP Method (GET, POST, PUT, DELETE)
     * @param {object|null} data - Request payload DTO
     * @returns {Promise<object>} JSON response from server
     */
    async request(endpoint, method = 'GET', data = null) {
        const config = {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        };

        if (data && (method === 'POST' || method === 'PUT')) {
            config.body = JSON.stringify(data);
        }

        try {
            const response = await fetch(`${this.baseUrl}${endpoint}`, config);
            const responseData = await response.json().catch(() => ({}));

            if (!response.ok) {
                const errorMessage = responseData.message || `Request failed with status ${response.status}`;
                throw new Error(errorMessage);
            }

            return responseData;
        } catch (error) {
            console.error(`API Error [${method} ${endpoint}]:`, error.message);
            throw error;
        }
    },

    // Convenience Methods
    get(endpoint) {
        return this.request(endpoint, 'GET');
    },

    post(endpoint, data) {
        return this.request(endpoint, 'POST', data);
    },

    put(endpoint, data) {
        return this.request(endpoint, 'PUT', data);
    },

    delete(endpoint) {
        return this.request(endpoint, 'DELETE');
    }
};

// Export to window object for global availability in frontend HTML files
window.ApiClient = ApiClient;