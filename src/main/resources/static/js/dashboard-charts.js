/**
 * AgriPulse Dashboard Chart Controller
 * Handles initialization, lazy observation, and dynamic re-rendering on visibility changes.
 */
class DashboardCharts {
    constructor() {
        this.instances = new Map();
        this.observer = null;
        this.init();
    }

    init() {
        if (typeof Chart === 'undefined') {
            console.error('[DashboardCharts] Chart.js runtime not found in window context.');
            return;
        }

        this.observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting && entry.target.dataset.chartId) {
                    this.renderChart(entry.target.dataset.chartId);
                }
            });
        }, { threshold: 0.1 });

        document.querySelectorAll('canvas[data-chart-id]').forEach(canvas => {
            this.observer.observe(canvas);
        });
    }

    renderChart(chartId) {
        const canvas = document.querySelector(`canvas[data-chart-id="${chartId}"]`);
        if (!canvas) return;

        const rect = canvas.getBoundingClientRect();
        if (rect.width === 0 || rect.height === 0) return;

        if (this.instances.has(chartId)) {
            this.instances.get(chartId).destroy();
        }

        let chartConfig = {
            type: 'line',
            data: {
                labels: ['Q1', 'Q2', 'Q3', 'Q4'],
                datasets: [{
                    label: 'Yield (Metric Tons)',
                    data: [12.4, 19.2, 15.8, 22.1],
                    borderColor: '#10b981',
                    backgroundColor: 'rgba(16, 185, 129, 0.1)',
                    tension: 0.35,
                    fill: true
                }]
            },
            options: { responsive: true, maintainAspectRatio: false }
        };

        const ctx = canvas.getContext('2d');
        const instance = new Chart(ctx, chartConfig);
        this.instances.set(chartId, instance);
        this.observer.unobserve(canvas);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    window.agriPulseCharts = new DashboardCharts();
});