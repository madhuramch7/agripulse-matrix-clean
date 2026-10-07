/**
 * AgriPulse Matrix Dashboard - Dynamic Chart.js Analytics Engine
 * Renders real-time graphs for soil pH, moisture, and N-P-K nutrient composition.
 */

const DashboardCharts = {
    instances: {},

    /**
     * Initializes or updates the Soil Health Composition Chart
     * @param {string} canvasId - HTML Canvas element ID
     * @param {object} soilMetrics - { nitrogen, phosphorus, potassium, ph, moisture }
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
    }
};

// Export to window object for global availability
window.DashboardCharts = DashboardCharts;