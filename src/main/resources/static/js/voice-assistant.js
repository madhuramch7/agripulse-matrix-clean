/**
 * AgriPulse Speech Recognition Engine
 * Provides cross-browser Web Speech API support, UI indicators, and form auto-population.
 */
class VoiceAssistant {
    constructor(buttonId, statusIndicatorId) {
        this.button = document.getElementById(buttonId);
        this.statusIndicator = document.getElementById(statusIndicatorId);
        this.recognition = null;
        this.isListening = false;

        this.init();
    }

    init() {
        const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;

        if (!SpeechRecognition) {
            console.warn('[VoiceAssistant] Web Speech API not supported in this browser.');
            if (this.button) {
                this.button.disabled = true;
                this.button.title = 'Speech recognition not supported in this browser';
                this.button.classList.add('opacity-40', 'cursor-not-allowed');
            }
            return;
        }

        this.recognition = new SpeechRecognition();
        this.recognition.continuous = false;
        this.recognition.interimResults = false;
        this.recognition.lang = 'en-IN';

        this.recognition.onstart = () => {
            this.isListening = true;
            this.updateUiState(true);
        };

        this.recognition.onend = () => {
            this.isListening = false;
            this.updateUiState(false);
        };

        this.recognition.onerror = (event) => {
            this.isListening = false;
            this.updateUiState(false);
            console.error('[VoiceAssistant] Recognition error:', event.error);
            this.notifyFeedback(this.getErrorMessage(event.error));
        };

        this.recognition.onresult = (event) => {
            if (event.results && event.results[0].length > 0) {
                const transcript = event.results[0][0].transcript.trim().toLowerCase();
                this.processCommand(transcript);
            }
        };

        if (this.button) {
            this.button.addEventListener('click', () => this.toggleListening());
        }
    }

    toggleListening() {
        if (!this.recognition) return;
        if (this.isListening) {
            this.recognition.stop();
        } else {
            try {
                this.recognition.start();
            } catch (err) {
                console.error('[VoiceAssistant] Start invocation error:', err);
            }
        }
    }

    updateUiState(listening) {
        if (!this.button) return;
        if (listening) {
            this.button.classList.add('bg-red-500', 'animate-pulse');
            this.button.classList.remove('bg-emerald-600');
            if (this.statusIndicator) this.statusIndicator.textContent = 'Listening... Speak now';
        } else {
            this.button.classList.remove('bg-red-500', 'animate-pulse');
            this.button.classList.add('bg-emerald-600');
            if (this.statusIndicator) this.statusIndicator.textContent = 'Mic idle';
        }
    }

    getErrorMessage(error) {
        switch (error) {
            case 'not-allowed': return 'Microphone permission denied.';
            case 'no-speech': return 'No speech detected. Try speaking closer to mic.';
            case 'network': return 'Network error during voice transmission.';
            default: return `Voice error: ${error}`;
        }
    }

    notifyFeedback(message) {
        if (this.statusIndicator) {
            this.statusIndicator.textContent = message;
            setTimeout(() => {
                if (!this.isListening && this.statusIndicator) {
                    this.statusIndicator.textContent = '';
                }
            }, 4000);
        }
    }

    processCommand(transcript) {
        console.log('[VoiceAssistant] Recognized:', transcript);
        this.notifyFeedback(`Processing: "${transcript}"`);

        if (transcript.includes('dashboard')) {
            window.location.href = 'dashboard.html';
            return;
        }
        if (transcript.includes('onboard') || transcript.includes('farmer')) {
            window.location.href = 'onboarding.html';
            return;
        }
        if (transcript.includes('loan') || transcript.includes('kcc')) {
            window.location.href = 'loans.html';
            return;
        }

        const landInput = document.querySelector('input[name="landSizeAcres"]');
        const landMatch = transcript.match(/(\d+(?:\.\d+)?)\s*(?:acres|acre)/i);
        if (landMatch && landInput) {
            landInput.value = landMatch[1];
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    window.agriVoice = new VoiceAssistant('voice-mic-btn', 'voice-status-feedback');
});