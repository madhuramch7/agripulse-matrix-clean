/**
 * AgriPulse Matrix Dashboard - Dynamic Chart.js Analytics Engine
 * Renders real-time graphs for soil pH, moisture, and N-P-K nutrient composition.
 */

const DashboardCharts = {
    instances: {},

    /**
     * Initializes or updates the Soil Health Composition Chart
     * @param {string} canvasId Canvas element ID
     * @param {object} soilMetrics { nitrogen, phosphorus, potassium, ph, moisture }
     */
    renderSoilNutrientChart(canvasId, soilMetrics) {
        const ctx = document.getElementById(canvasId);
        if (!ctx) return;

        // Destroy pre-existing chart instance if re-rendering dynamically
        if (this.instances[canvasId]) {
            this.instances[canvasId].destroy();
        }

        this.instances[canvasId] = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Nitrogen (N)', 'Phosphorus (P)', 'Potassium (K)', 'Moisture (%)'],
                datasets: [{
                    label: 'Current Field Levels',
                    data: [
                        soilMetrics.nitrogen || 0,
                        soilMetrics.phosphorus || 0,
                        soilMetrics.potassium || 0,
                        soilMetrics.moisture || 0
                    ],
                    backgroundColor: [
                        'rgba(34, 197, 94, 0.7)',
                        'rgba(59, 130, 246, 0.7)',
                        'rgba(168, 85, 247, 0.7)',
                        'rgba(14, 165, 233, 0.7)'
                    ],
                    borderColor: [
                        '#22c55e',
                        '#3b82f6',
                        '#a855f7',
                        '#0ea5e9'
                    ],
                    borderWidth: 2,
                    borderRadius: 8
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false },
                    tooltip: { enabled: true }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        grid: { color: 'rgba(255, 255, 255, 0.1)' },
                        ticks: { color: '#9ca3af' }
                    },
                    x: {
                        grid: { display: false },
                        ticks: { color: '#9ca3af' }
                    }
                }
            }
        });
    },

    /**
     * Renders the Graph & Illustration-Based Diagnosis Telemetry Chart
     * @param {string} canvasId Canvas element ID
     * @param {object} diagnosisData { nitrogen, phosphorus, potassium, ph }
     */
    renderSoilDiagnosisChart(canvasId, diagnosisData) {
        const ctx = document.getElementById(canvasId);
        if (!ctx) return;

        if (this.instances[canvasId]) {
            this.instances[canvasId].destroy();
        }

        this.instances[canvasId] = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Nitrogen (N)', 'Phosphorus (P)', 'Potassium (K)', 'pH Level'],
                datasets: [{
                    label: 'Farmer Telemetry Values',
                    data: [
                        diagnosisData.nitrogen || 0,
                        diagnosisData.phosphorus || 0,
                        diagnosisData.potassium || 0,
                        diagnosisData.ph || 0
                    ],
                    backgroundColor: [
                        'rgba(212, 175, 55, 0.8)', // Golden Amber accent
                        'rgba(46, 139, 87, 0.8)',  // Forest Green
                        'rgba(60, 179, 113, 0.8)', // Medium Sea Green
                        'rgba(244, 164, 96, 0.8)'  // Sandy Brown
                    ],
                    borderColor: [
                        '#D4AF37',
                        '#2E8B57',
                        '#3CB371',
                        '#F4A460'
                    ],
                    borderWidth: 2,
                    borderRadius: 8
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false },
                    tooltip: { enabled: true }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        grid: { color: 'rgba(212, 175, 55, 0.1)' },
                        ticks: { color: '#FAF8F5' }
                    },
                    x: {
                        grid: { display: false },
                        ticks: { color: '#FAF8F5' }
                    }
                }
            }
        });
    }
};

// Export to window object for global availability
window.DashboardCharts = DashboardCharts;