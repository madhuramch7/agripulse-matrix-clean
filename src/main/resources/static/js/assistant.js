(function() {
    // Prevent double injection
    if (document.getElementById('bhashini-ai-fab')) return;

    // Create Floating Action Button (FAB)
    const fab = document.createElement('div');
    fab.id = 'bhashini-ai-fab';
    fab.innerHTML = '<i class="fa-solid fa-wand-magic-sparkles text-2xl font-bold"></i>';
    fab.title = 'Bhashini AI Assistant (22 Languages)';
    document.body.appendChild(fab);

    // Create Chat Window Container (Minimized by default)
    const win = document.createElement('div');
    win.id = 'bhashini-ai-window';
    win.innerHTML = `
        <div class="bg-[#041C14] text-white px-4 py-3 flex items-center justify-between border-b border-[#D4AF37]/30">
            <div class="flex items-center space-x-2">
                <div class="w-8 h-8 rounded-lg bg-[#D4AF37] flex items-center justify-center text-[#041C14] font-bold">
                    <i class="fa-solid fa-wheat-awn text-xs"></i>
                </div>
                <div>
                    <h3 class="text-xs font-bold text-[#F4EBE1]">Bhashini AI Assistant</h3>
                    <p class="text-[10px] text-[#D4AF37]">22 Indian Languages & Voice Navigation</p>
                </div>
            </div>
            <button id="bhashini-close" class="text-[#C2D0C5] hover:text-white text-sm"><i class="fa-solid fa-xmark"></i></button>
        </div>
        <div id="bhashini-messages" class="flex-1 p-4 overflow-y-auto space-y-3 text-xs bg-[#FAF8F5]">
            <div class="bg-white border border-[#EAE3D2] p-3 rounded-xl shadow-sm text-[#3D312A]">
                <p class="font-bold text-[#A0522D] mb-1">Namaste! 🙏</p>
                <p>I am your Bhashini AI Assistant. Ask me anything about your soil telemetry, crop loans, government schemes, or tell me to navigate to any section in 22 regional languages!</p>
            </div>
        </div>
        <div class="p-3 bg-white border-t border-[#EAE3D2] flex items-center space-x-2">
            <select id="bhashini-lang" class="bg-[#FAF8F5] border border-[#EAE3D2] rounded-lg text-[10px] p-1 text-[#3D312A] focus:outline-none">
                <option value="en">English</option>
                <option value="hi">हिंदी (Hindi)</option>
                <option value="mr">मराठी (Marathi)</option>
                <option value="gu">ગુજરાती (Gujarati)</option>
                <option value="bn">বাংলা (Bengali)</option>
                <option value="ta">தமிழ் (Tamil)</option>
                <option value="te">తెలుగు (Telugu)</option>
            </select>
            <input type="text" id="bhashini-input" placeholder="Type or use voice..." class="flex-1 bg-[#FAF8F5] border border-[#EAE3D2] rounded-xl px-3 py-2 text-xs text-[#3D312A] focus:outline-none focus:border-[#A0522D]">
            <button id="bhashini-send" class="bg-[#A0522D] hover:bg-[#804122] text-white px-3 py-2 rounded-xl text-xs font-bold transition-colors shadow-sm">
                <i class="fa-solid fa-paper-plane"></i>
            </button>
        </div>
    `;
    document.body.appendChild(win);

    // Toggle Window Visibility
    fab.addEventListener('click', () => {
        win.classList.toggle('open');
    });

    document.getElementById('bhashini-close').addEventListener('click', () => {
        win.classList.remove('open');
    });

    // Handle Chat Submission & Navigation
    const input = document.getElementById('bhashini-input');
    const sendBtn = document.getElementById('bhashini-send');
    const msgContainer = document.getElementById('bhashini-messages');

    async function handleUserQuery() {
        const text = input.value.trim();
        if (!text) return;

        // Append user message
        const userMsg = document.createElement('div');
        userMsg.className = 'bg-[#041C14] text-white p-2.5 rounded-xl ml-6 shadow-sm';
        userMsg.textContent = text;
        msgContainer.appendChild(userMsg);
        input.value = '';
        msgContainer.scrollTop = msgContainer.scrollHeight;

        // Check for navigation commands across tabs
        const lower = text.toLowerCase();
        let botReply = "I have processed your request through the Bhashini multilingual engine.";
        if (lower.includes('loan') || lower.includes('credit') || lower.includes('sbi')) {
            botReply = "Navigating you to the APCI Credit Score & Bank Portal...";
            setTimeout(() => { window.location.href = '/loans.html'; }, 1200);
        } else if (lower.includes('dashboard') || lower.includes('soil') || lower.includes('analytics')) {
            botReply = "Navigating to your Comprehensive Soil & Crop Dashboard...";
            setTimeout(() => { window.location.href = '/dashboard.html'; }, 1200);
        } else if (lower.includes('trace') || lower.includes('qr') || lower.includes('batch')) {
            botReply = "Opening Farm-to-Consumer QR Traceability Hub...";
            setTimeout(() => { window.location.href = '/traceability.html'; }, 1200);
        } else {
            try {
                const res = await fetch('/api/ai/chat', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ message: text, lang: document.getElementById('bhashini-lang').value }),
                    credentials: 'same-origin'
                });
                if (res.ok) {
                    const data = await res.json();
                    botReply = data.reply || botReply;
                }
            } catch (e) {}
        }

        // Append bot response
        const botMsg = document.createElement('div');
        botMsg.className = 'bg-white border border-[#EAE3D2] text-[#3D312A] p-2.5 rounded-xl mr-6 shadow-sm';
        botMsg.textContent = botReply;
        msgContainer.appendChild(botMsg);
        msgContainer.scrollTop = msgContainer.scrollHeight;
    }

    sendBtn.addEventListener('click', handleUserQuery);
    input.addEventListener('keypress', (e) => { if (e.key === 'Enter') handleUserQuery(); });
})();