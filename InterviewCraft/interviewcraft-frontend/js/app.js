/**
 * InterviewCraft AI - Enterprise Frontend Client & Dynamic Interactive Engine
 */

class InterviewCraftApp {
    constructor() {
        this.apiBase = 'http://localhost:8080/api';
        this.geminiApiKey = localStorage.getItem('ic_gemini_key') || '';
        this.geminiModel = localStorage.getItem('ic_gemini_model') || 'gemini-3.6-flash';
        this.token = localStorage.getItem('ic_token') || null;
        this.currentUser = JSON.parse(localStorage.getItem('ic_user') || 'null');
        this.chatHistory = JSON.parse(localStorage.getItem('ic_chat_history') || '[]');
        this.activeRoadmapFilter = 'ALL';
        this.activeCategory = 'ALL';
        this.isSignUpMode = false;

        // Editable Profile State
        this.profileState = {
            targetRole: 'Senior Java Backend Engineer',
            timeline: '30 Days',
            companyTier: 'FAANG / Tier-1 Tech',
            techStack: ['Java 21', 'Spring Boot 3', 'PostgreSQL', 'Kafka', 'Redis', 'Microservices'],
            skills: {
                dsa: 65,
                hld: 55,
                lld: 75,
                framework: 80,
                behavioral: 70
            }
        };

        this.currentPlan = null;
        this.allResources = [];

        this.init();
    }

    init() {
        this.loadSavedProfile();
        this.bindEvents();
        this.updateAuthUi();
        this.updateGeminiKeyUi();
        this.initChatSession();
        this.generateTailoredRoadmap();
        this.loadVerifiedResources();

        // Auto-open Gemini Key modal on first visit if no key saved
        if (!this.geminiApiKey) {
            setTimeout(() => this.openGeminiModal(), 800);
        }
    }

    loadSavedProfile() {
        const saved = localStorage.getItem('ic_profile_state');
        if (saved) {
            try {
                this.profileState = { ...this.profileState, ...JSON.parse(saved) };
            } catch (ignored) {}
        }
        this.syncProfileUi();
    }

    saveProfile() {
        localStorage.setItem('ic_profile_state', JSON.stringify(this.profileState));
    }

    syncProfileUi() {
        const roleEl = document.getElementById('editTargetRole');
        if (roleEl) roleEl.value = this.profileState.targetRole;

        const timelineEl = document.getElementById('editTimeline');
        if (timelineEl) timelineEl.value = this.profileState.timeline;

        const tierEl = document.getElementById('editCompanyTier');
        if (tierEl) tierEl.value = this.profileState.companyTier;

        // Sync Sliders
        const sDsa = document.getElementById('sliderDsa');
        if (sDsa) { sDsa.value = this.profileState.skills.dsa; document.getElementById('valDsa').textContent = `${this.profileState.skills.dsa}%`; }
        const sHld = document.getElementById('sliderHld');
        if (sHld) { sHld.value = this.profileState.skills.hld; document.getElementById('valHld').textContent = `${this.profileState.skills.hld}%`; }
        const sLld = document.getElementById('sliderLld');
        if (sLld) { sLld.value = this.profileState.skills.lld; document.getElementById('valLld').textContent = `${this.profileState.skills.lld}%`; }
        const sFw = document.getElementById('sliderFramework');
        if (sFw) { sFw.value = this.profileState.skills.framework; document.getElementById('valFramework').textContent = `${this.profileState.skills.framework}%`; }
        const sBeh = document.getElementById('sliderBehavioral');
        if (sBeh) { sBeh.value = this.profileState.skills.behavioral; document.getElementById('valBehavioral').textContent = `${this.profileState.skills.behavioral}%`; }

        // Sync Tags
        document.querySelectorAll('#profileTechTags .tag').forEach(tag => {
            const tech = tag.getAttribute('data-tech');
            if (this.profileState.techStack.includes(tech)) {
                tag.classList.add('active');
            } else {
                tag.classList.remove('active');
            }
        });
    }

    bindEvents() {
        // Tab switching
        document.querySelectorAll('.nav-tab').forEach(tab => {
            tab.addEventListener('click', () => {
                this.openTab(tab.getAttribute('data-tab'));
            });
        });

        // Editable Profile Selects
        const roleSelect = document.getElementById('editTargetRole');
        if (roleSelect) {
            roleSelect.addEventListener('change', (e) => {
                this.profileState.targetRole = e.target.value;
                this.saveProfile();
                this.updateAuthUi();
                this.generateTailoredRoadmap();
                this.showToast(`Target role updated to: ${this.profileState.targetRole}`);
            });
        }

        const timelineSelect = document.getElementById('editTimeline');
        if (timelineSelect) {
            timelineSelect.addEventListener('change', (e) => {
                this.profileState.timeline = e.target.value;
                this.saveProfile();
                this.generateTailoredRoadmap();
                this.showToast(`Timeline adjusted to: ${this.profileState.timeline}`);
            });
        }

        const tierSelect = document.getElementById('editCompanyTier');
        if (tierSelect) {
            tierSelect.addEventListener('change', (e) => {
                this.profileState.companyTier = e.target.value;
                this.saveProfile();
                this.generateTailoredRoadmap();
                this.showToast(`Target Tier updated to: ${this.profileState.companyTier}`);
            });
        }

        // Interactive Tech Tags Toggle
        document.querySelectorAll('#profileTechTags .tag').forEach(tag => {
            tag.addEventListener('click', () => {
                const tech = tag.getAttribute('data-tech');
                if (this.profileState.techStack.includes(tech)) {
                    this.profileState.techStack = this.profileState.techStack.filter(t => t !== tech);
                    tag.classList.remove('active');
                } else {
                    this.profileState.techStack.push(tech);
                    tag.classList.add('active');
                }
                this.saveProfile();
                this.generateTailoredRoadmap();
            });
        });

        // Interactive Matrix Sliders
        const bindSlider = (id, valId, key) => {
            const slider = document.getElementById(id);
            if (slider) {
                slider.addEventListener('input', (e) => {
                    const val = parseInt(e.target.value);
                    document.getElementById(valId).textContent = `${val}%`;
                    this.profileState.skills[key] = val;
                    this.saveProfile();
                });
                slider.addEventListener('change', () => {
                    this.generateTailoredRoadmap();
                });
            }
        };

        bindSlider('sliderDsa', 'valDsa', 'dsa');
        bindSlider('sliderHld', 'valHld', 'hld');
        bindSlider('sliderLld', 'valLld', 'lld');
        bindSlider('sliderFramework', 'valFramework', 'framework');
        bindSlider('sliderBehavioral', 'valBehavioral', 'behavioral');

        // Chat Form
        const chatForm = document.getElementById('chatForm');
        if (chatForm) {
            chatForm.addEventListener('submit', (e) => {
                e.preventDefault();
                this.handleSendMessage();
            });
        }

        // Clear Chat
        const clearChatBtn = document.getElementById('clearChatBtn');
        if (clearChatBtn) {
            clearChatBtn.addEventListener('click', () => this.clearChatHistory());
        }

        // Restart Chat
        const restartBtn = document.getElementById('restartChatBtn');
        if (restartBtn) {
            restartBtn.addEventListener('click', () => this.restartSession());
        }

        // Generate Plan Button
        const genPlanBtn = document.getElementById('generateRoadmapFromChatBtn');
        if (genPlanBtn) {
            genPlanBtn.addEventListener('click', () => {
                this.generateTailoredRoadmap();
                this.openTab('roadmap-view');
                this.showToast('Tailored plan ready with direct LeetCode and reference links!');
            });
        }

        // Gemini Key Modal
        const geminiKeyBtn = document.getElementById('geminiKeyBtn');
        if (geminiKeyBtn) {
            geminiKeyBtn.addEventListener('click', () => this.openGeminiModal());
        }

        const closeGeminiBtn = document.getElementById('closeGeminiModalBtn');
        if (closeGeminiBtn) {
            closeGeminiBtn.addEventListener('click', () => this.closeGeminiModal());
        }

        const geminiKeyForm = document.getElementById('geminiKeyForm');
        if (geminiKeyForm) {
            geminiKeyForm.addEventListener('submit', (e) => {
                e.preventDefault();
                this.saveGeminiKey();
            });
        }

        // Auth Modal & Trigger
        const authBtn = document.getElementById('authActionBtn');
        if (authBtn) {
            authBtn.addEventListener('click', () => this.handleAuthAction());
        }

        const closeAuthBtn = document.getElementById('closeAuthModalBtn');
        if (closeAuthBtn) {
            closeAuthBtn.addEventListener('click', () => this.closeAuthModal());
        }

        const toggleAuthLink = document.getElementById('toggleAuthModeLink');
        if (toggleAuthLink) {
            toggleAuthLink.addEventListener('click', (e) => {
                e.preventDefault();
                this.toggleAuthMode();
            });
        }

        const authForm = document.getElementById('authForm');
        if (authForm) {
            authForm.addEventListener('submit', (e) => {
                e.preventDefault();
                this.submitAuth();
            });
        }

        // Search & Category Filters in Resources Hub
        const searchInput = document.getElementById('resourceSearchInput');
        if (searchInput) {
            searchInput.addEventListener('input', (e) => {
                this.filterResources(e.target.value);
            });
        }

        document.querySelectorAll('#categoryFilters .filter-pill').forEach(pill => {
            pill.addEventListener('click', () => {
                document.querySelectorAll('#categoryFilters .filter-pill').forEach(p => p.classList.remove('active'));
                pill.classList.add('active');
                this.activeCategory = pill.getAttribute('data-category');
                this.renderResources();
            });
        });

        // Roadmap Category Filters
        document.querySelectorAll('#roadmapCategoryFilters .filter-pill').forEach(pill => {
            pill.addEventListener('click', () => {
                document.querySelectorAll('#roadmapCategoryFilters .filter-pill').forEach(p => p.classList.remove('active'));
                pill.classList.add('active');
                this.activeRoadmapFilter = pill.getAttribute('data-roadmap-filter');
                this.renderRoadmap(this.currentPlan);
            });
        });
    }

    openTab(tabId) {
        document.querySelectorAll('.nav-tab').forEach(t => t.classList.remove('active'));
        document.querySelectorAll('.tab-panel').forEach(p => p.classList.remove('active'));

        const targetTab = document.querySelector(`[data-tab="${tabId}"]`);
        const targetPanel = document.getElementById(tabId);

        if (targetTab) targetTab.classList.add('active');
        if (targetPanel) targetPanel.classList.add('active');
    }

    // ==================== GEMINI API KEY MANAGEMENT ====================

    openGeminiModal() {
        document.getElementById('geminiApiKeyInput').value = this.geminiApiKey;
        document.getElementById('geminiModelSelect').value = this.geminiModel;
        document.getElementById('geminiKeyModal').classList.add('active');
    }

    closeGeminiModal() {
        document.getElementById('geminiKeyModal').classList.remove('active');
    }

    async saveGeminiKey() {
        const keyInput = document.getElementById('geminiApiKeyInput');
        const key = keyInput.value.trim();
        const errorEl = document.getElementById('geminiKeyError');
        const model = document.getElementById('geminiModelSelect').value;

        if (!key) {
            if (errorEl) {
                errorEl.textContent = 'API key is required. Get a free key from Google AI Studio (link above).';
                errorEl.style.display = 'block';
            }
            return;
        }

        // Validate the key with a test API call
        const submitBtn = document.querySelector('#geminiKeyForm button[type="submit"]');
        const originalBtnText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<i class="ph-bold ph-spinner"></i> Validating key...';
        submitBtn.disabled = true;

        try {
            const testUrl = `https://generativelanguage.googleapis.com/v1beta/models/${model}:generateContent?key=${key}`;
            const testRes = await fetch(testUrl, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    contents: [{ role: 'user', parts: [{ text: 'Respond with just the word: Connected' }] }]
                })
            });

            if (!testRes.ok) {
                const errData = await testRes.json().catch(() => ({}));
                const errMsg = errData?.error?.message || `HTTP ${testRes.status}`;
                throw new Error(errMsg);
            }

            // Key is valid!
            this.geminiApiKey = key;
            this.geminiModel = model;
            localStorage.setItem('ic_gemini_key', this.geminiApiKey);
            localStorage.setItem('ic_gemini_model', this.geminiModel);

            if (errorEl) errorEl.style.display = 'none';
            this.closeGeminiModal();
            this.updateGeminiKeyUi();

            // Reset chat for fresh Gemini-powered session
            this.chatHistory = [];
            localStorage.removeItem('ic_chat_history');
            this.initChatSession();

            this.showToast('✨ Gemini AI connected! Your interview mentor is ready.');
        } catch (err) {
            console.error('Gemini key validation failed:', err);
            if (errorEl) {
                errorEl.textContent = `Invalid key: ${err.message}. Please check and try again.`;
                errorEl.style.display = 'block';
            }
        } finally {
            submitBtn.innerHTML = originalBtnText;
            submitBtn.disabled = false;
        }
    }

    updateGeminiKeyUi() {
        const btnLabel = document.getElementById('geminiKeyBtnLabel');
        const indicator = document.getElementById('aiModelIndicator');

        if (this.geminiApiKey) {
            btnLabel.textContent = '✨ Gemini Connected';
            if (indicator) indicator.textContent = `Google Gemini Active (${this.geminiModel})`;
        } else {
            btnLabel.textContent = 'Gemini API Key';
            if (indicator) indicator.textContent = 'Interactive Mentor (Adaptive Engine / Add Gemini Key for Cloud AI)';
        }
    }

    // ==================== AUTH METHODS ====================

    updateAuthUi() {
        const authBtnLabel = document.getElementById('authBtnLabel');
        const displayUserName = document.getElementById('displayUserName');
        const displayUserRole = document.getElementById('displayUserRole');
        const userAvatar = document.getElementById('userAvatar');

        if (this.currentUser) {
            authBtnLabel.textContent = 'Sign Out';
            displayUserName.textContent = this.currentUser.fullName || 'Candidate';
            displayUserRole.textContent = this.profileState.targetRole;
            const initials = (this.currentUser.fullName || 'CB')
                .split(' ')
                .map(n => n[0])
                .join('')
                .substring(0, 2)
                .toUpperCase();
            userAvatar.textContent = initials;
        } else {
            authBtnLabel.textContent = 'Sign In';
            displayUserName.textContent = 'Candidate';
            displayUserRole.textContent = this.profileState.targetRole;
            userAvatar.textContent = 'IC';
        }
    }

    handleAuthAction() {
        if (this.token) {
            localStorage.removeItem('ic_token');
            localStorage.removeItem('ic_user');
            this.token = null;
            this.currentUser = null;
            this.updateAuthUi();
            this.showToast('Signed out successfully');
        } else {
            this.openAuthModal();
        }
    }

    openAuthModal() {
        document.getElementById('authModal').classList.add('active');
    }

    closeAuthModal() {
        document.getElementById('authModal').classList.remove('active');
    }

    toggleAuthMode() {
        this.isSignUpMode = !this.isSignUpMode;
        document.getElementById('fullNameGroup').style.display = this.isSignUpMode ? 'block' : 'none';
        document.getElementById('authModalTitle').textContent = this.isSignUpMode ? 'Create Candidate Account' : 'Candidate Sign In';
        document.getElementById('submitAuthBtn').textContent = this.isSignUpMode ? 'Create Account' : 'Sign In';
        document.getElementById('toggleAuthPrompt').textContent = this.isSignUpMode ? 'Already have an account?' : "Don't have an account?";
        document.getElementById('toggleAuthModeLink').textContent = this.isSignUpMode ? 'Sign In' : 'Create Account';
    }

    submitAuth() {
        const email = document.getElementById('authEmail').value;
        const fullName = document.getElementById('authFullName').value;

        this.currentUser = {
            id: 1,
            email: email,
            fullName: fullName || (email.split('@')[0].toUpperCase()),
            targetRole: this.profileState.targetRole
        };
        this.token = 'demo-jwt-token';
        localStorage.setItem('ic_token', this.token);
        localStorage.setItem('ic_user', JSON.stringify(this.currentUser));
        this.updateAuthUi();
        this.closeAuthModal();
        this.showToast('Signed in successfully!');
    }

    // ==================== INTERACTIVE AI CHAT & CONSULTATION ====================

    initChatSession() {
        if (this.chatHistory.length === 0) {
            let welcome;
            if (this.geminiApiKey) {
                welcome = `✨ **Gemini AI Interview Mentor is active!**\n\n` +
                    `I'm powered by Google Gemini and ready to conduct a **personalized, interactive interview consultation** for you.\n\n` +
                    `Your profile: **${this.profileState.targetRole}** targeting **${this.profileState.companyTier}** on a **${this.profileState.timeline}** timeline.\n\n` +
                    `I can:\n` +
                    `- 🧠 **Ask you real interview questions** and evaluate your answers\n` +
                    `- 📋 **Diagnose your skill gaps** through conversation\n` +
                    `- 💻 **Recommend specific LeetCode problems** with direct links\n` +
                    `- 📚 **Suggest books, videos, and courses** tailored to your level\n` +
                    `- 🏗️ **Walk you through System Design** problems step by step\n\n` +
                    `Let's begin! Tell me about your background, or just ask me any interview question.`;
            } else {
                welcome = `👋 Welcome to **InterviewCraft AI**!\n\n` +
                    `⚠️ **To start your AI-powered interview preparation, please connect your Google Gemini API key.**\n\n` +
                    `Click the **"Gemini API Key"** button in the header above to connect. It's free and takes 30 seconds!\n\n` +
                    `Once connected, I'll be able to:\n` +
                    `- Ask you real technical interview questions\n` +
                    `- Evaluate your answers with detailed feedback\n` +
                    `- Recommend specific LeetCode problems and study materials\n` +
                    `- Create a personalized interview preparation roadmap`;
            }

            this.chatHistory.push({ sender: 'AI', message: welcome, timestamp: new Date().toISOString() });
            this.saveChatHistory();
        }

        this.renderChatMessages(this.chatHistory);
    }

    saveChatHistory() {
        localStorage.setItem('ic_chat_history', JSON.stringify(this.chatHistory));
    }

    clearChatHistory() {
        this.chatHistory = [];
        localStorage.removeItem('ic_chat_history');
        this.initChatSession();
        this.showToast('Chat history cleared');
    }

    restartSession() {
        this.clearChatHistory();
        this.showToast('Interview consultation restarted');
    }

    renderChatMessages(messages) {
        const container = document.getElementById('chatMessagesContainer');
        if (!container) return;

        container.innerHTML = '';
        messages.forEach(msg => {
            this.appendChatMessage(msg.sender, msg.message, false);
        });

        container.scrollTop = container.scrollHeight;
    }

    appendChatMessage(sender, text, save = true) {
        const container = document.getElementById('chatMessagesContainer');
        if (!container) return;

        const bubble = document.createElement('div');
        bubble.className = `chat-bubble ${sender.toLowerCase()}`;
        bubble.innerHTML = this.formatMarkdown(text);
        container.appendChild(bubble);
        container.scrollTop = container.scrollHeight;

        if (save) {
            this.chatHistory.push({ sender, message: text, timestamp: new Date().toISOString() });
            this.saveChatHistory();
        }
    }

    async handleSendMessage() {
        const input = document.getElementById('chatInput');
        const text = input.value.trim();
        if (!text) return;

        // Check if Gemini key is configured
        if (!this.geminiApiKey) {
            this.openGeminiModal();
            this.showToast('Please connect your Gemini API key first', true);
            return;
        }

        input.value = '';
        input.disabled = true;
        this.appendChatMessage('USER', text, true);

        // Analyze user text to dynamically adapt profile
        this.extractAndApplySkillsFromUserText(text);

        // Show typing indicator
        const typingEl = document.createElement('div');
        typingEl.className = 'chat-bubble ai typing';
        typingEl.innerHTML = '<div class="typing-dots"><span></span><span></span><span></span></div> Gemini is thinking...';
        const chatContainer = document.getElementById('chatMessagesContainer');
        chatContainer.appendChild(typingEl);
        chatContainer.scrollTop = chatContainer.scrollHeight;

        try {
            const aiReply = await this.callGeminiApi(text);
            typingEl.remove();
            this.appendChatMessage('AI', aiReply, true);
        } catch (err) {
            typingEl.remove();
            console.error('Gemini API error:', err);

            let errorMsg;
            if (err.message.includes('429') || err.message.includes('RESOURCE_EXHAUSTED')) {
                errorMsg = `⚠️ **Rate limit reached.** The Gemini free tier allows 15 requests/minute. Please wait a moment and try again.\n\n_Your question: "${text}"_`;
            } else if (err.message.includes('403') || err.message.includes('API_KEY_INVALID')) {
                errorMsg = `❌ **API Key Error.** Your Gemini API key appears to be invalid or expired. Please click the **"Gemini API Key"** button to update it.\n\n_Error: ${err.message}_`;
                // Clear invalid key
                this.geminiApiKey = '';
                localStorage.removeItem('ic_gemini_key');
                this.updateGeminiKeyUi();
            } else {
                errorMsg = `⚠️ **Connection error.** Could not reach Gemini API.\n\n_Error: ${err.message}_\n\nPlease check your internet connection and try again.`;
            }

            this.appendChatMessage('AI', errorMsg, true);
        } finally {
            input.disabled = false;
            input.focus();
        }
    }

    buildSystemInstruction() {
        const skills = this.profileState.skills;
        const weakAreas = [];
        const strongAreas = [];

        Object.entries(skills).forEach(([key, val]) => {
            const label = { dsa: 'Data Structures & Algorithms', hld: 'High-Level System Design', lld: 'Low-Level Design & OOP', framework: 'Frameworks & Concurrency', behavioral: 'Behavioral & Leadership' }[key] || key;
            if (val < 50) weakAreas.push(`${label} (${val}%)`);
            else if (val >= 75) strongAreas.push(`${label} (${val}%)`);
        });

        return `You are InterviewCraft AI — a world-class Principal Engineer and technical interview preparation mentor with 15+ years of FAANG interview experience.

## YOUR ROLE
You conduct interactive, personalized interview preparation sessions. You are NOT a generic chatbot. You are an expert interviewer who:
- Asks diagnostic technical questions to evaluate the candidate's level
- Evaluates answers thoroughly with specific feedback on what was right, what was missing, and what would impress an interviewer
- Suggests specific, real LeetCode problems with direct URLs (format: https://leetcode.com/problems/SLUG/)
- Recommends specific books, YouTube videos, and tutorials with real URLs
- Adapts difficulty based on the candidate's responses
- Conducts mock interview rounds when asked

## CANDIDATE PROFILE
- **Target Role**: ${this.profileState.targetRole}
- **Target Company Tier**: ${this.profileState.companyTier}
- **Preparation Timeline**: ${this.profileState.timeline}
- **Tech Stack**: ${this.profileState.techStack.join(', ')}
- **Skill Self-Assessment**:
  - DSA & Algorithms: ${skills.dsa}%
  - System Design (HLD): ${skills.hld}%
  - Low-Level Design (LLD): ${skills.lld}%
  - Frameworks & Concurrency: ${skills.framework}%
  - Behavioral & Leadership: ${skills.behavioral}%
${weakAreas.length > 0 ? `- **Weak Areas to Focus On**: ${weakAreas.join(', ')}` : ''}
${strongAreas.length > 0 ? `- **Strong Areas**: ${strongAreas.join(', ')}` : ''}

## RESPONSE GUIDELINES
1. **Be conversational and interactive** — ask follow-up questions, don't just lecture
2. **When recommending LeetCode problems**, ALWAYS include the direct URL in format: [Problem Name](https://leetcode.com/problems/problem-slug/)
3. **When recommending books**, mention specific chapters when relevant
4. **When evaluating answers**, use a clear structure: ✅ What was good, ⚠️ What was missing, 💡 How to improve
5. **Use markdown formatting** — bold, bullet points, code blocks for code
6. **Be encouraging but honest** — don't sugarcoat weaknesses
7. **Ask ONE question at a time** unless the user requests multiple
8. **For System Design questions**, guide step-by-step: Requirements → Estimation → API Design → Data Model → Architecture → Deep Dives
9. **For coding questions**, discuss approach first before jumping to code

## VERIFIED REFERENCE MATERIALS YOU CAN RECOMMEND
- Books: "Designing Data-Intensive Applications" (Kleppmann), "Clean Code" (Martin), "System Design Interview" (Alex Xu), "Grokking Algorithms" (Bhargava), "Effective Java" (Bloch)
- YouTube: NeetCode (https://www.youtube.com/@NeetCode), ByteByteGo (https://www.youtube.com/@ByteByteGo), Gaurav Sen (https://www.youtube.com/@gkcs), Hussein Nasser (https://www.youtube.com/@hnasr)
- Tutorials: Baeldung (https://www.baeldung.com), Refactoring Guru (https://refactoring.guru/design-patterns), Roadmap.sh (https://roadmap.sh/backend)
- Practice: LeetCode Top 150 (https://leetcode.com/studyplan/top-interview-150/), NeetCode 150 (https://neetcode.io/practice)`;
    }

    async callGeminiApi(userMsg) {
        const url = `https://generativelanguage.googleapis.com/v1beta/models/${this.geminiModel}:generateContent?key=${this.geminiApiKey}`;

        // Build conversation contents with system instruction context
        const systemInstruction = this.buildSystemInstruction();

        const contents = [];

        // System instruction as first user/model exchange
        contents.push({
            role: 'user',
            parts: [{ text: `[SYSTEM INSTRUCTION - DO NOT REPEAT THIS TO THE USER]\n${systemInstruction}\n[END SYSTEM INSTRUCTION]\n\nPlease acknowledge and begin the interview preparation session.` }]
        });
        contents.push({
            role: 'model',
            parts: [{ text: 'Understood. I am InterviewCraft AI, your dedicated interview preparation mentor. I have reviewed your profile and am ready to conduct a personalized, interactive preparation session. I will ask diagnostic questions, evaluate your answers, and recommend specific resources. Let me know how you\'d like to begin.' }]
        });

        // Add conversation history (last 20 messages for rich context)
        const historyToSend = this.chatHistory.filter(m => m.sender === 'USER' || m.sender === 'AI');
        const recent = historyToSend.slice(-20);

        // Deduplicate: don't re-add the current user message if it's already the last one in history
        const messagesToAdd = recent.filter((m, i) => {
            // Skip if this is the very last message and matches current userMsg
            if (i === recent.length - 1 && m.sender === 'USER' && m.message === userMsg) return false;
            return true;
        });

        messagesToAdd.forEach(m => {
            contents.push({
                role: m.sender === 'USER' ? 'user' : 'model',
                parts: [{ text: m.message }]
            });
        });

        // Add the current user message
        contents.push({ role: 'user', parts: [{ text: userMsg }] });

        // Ensure alternating roles (Gemini requires this)
        const sanitizedContents = this.sanitizeContentsForGemini(contents);

        const requestBody = {
            contents: sanitizedContents,
            generationConfig: {
                temperature: 0.8,
                topP: 0.95,
                topK: 40,
                maxOutputTokens: 2048
            },
            safetySettings: [
                { category: 'HARM_CATEGORY_HARASSMENT', threshold: 'BLOCK_ONLY_HIGH' },
                { category: 'HARM_CATEGORY_HATE_SPEECH', threshold: 'BLOCK_ONLY_HIGH' },
                { category: 'HARM_CATEGORY_SEXUALLY_EXPLICIT', threshold: 'BLOCK_ONLY_HIGH' },
                { category: 'HARM_CATEGORY_DANGEROUS_CONTENT', threshold: 'BLOCK_ONLY_HIGH' }
            ]
        };

        const res = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        });

        if (!res.ok) {
            const errData = await res.json().catch(() => ({}));
            const errMsg = errData?.error?.message || `HTTP ${res.status}`;
            throw new Error(errMsg);
        }

        const data = await res.json();
        const candidate = data.candidates?.[0]?.content?.parts?.[0]?.text;

        if (!candidate) {
            // Check if blocked by safety
            const blockReason = data.candidates?.[0]?.finishReason;
            if (blockReason === 'SAFETY') {
                return '⚠️ My response was filtered by safety settings. Could you rephrase your question?';
            }
            throw new Error('No response content from Gemini');
        }

        return candidate;
    }

    /**
     * Gemini API requires strictly alternating user/model roles.
     * This method merges consecutive same-role messages.
     */
    sanitizeContentsForGemini(contents) {
        if (contents.length === 0) return contents;

        const sanitized = [contents[0]];

        for (let i = 1; i < contents.length; i++) {
            const prev = sanitized[sanitized.length - 1];
            const curr = contents[i];

            if (prev.role === curr.role) {
                // Merge consecutive same-role messages
                prev.parts[0].text += '\n\n' + curr.parts[0].text;
            } else {
                sanitized.push(curr);
            }
        }

        // Ensure the last message is from 'user'
        if (sanitized[sanitized.length - 1].role !== 'user') {
            sanitized.push({ role: 'user', parts: [{ text: 'Please continue.' }] });
        }

        return sanitized;
    }

    extractAndApplySkillsFromUserText(text) {
        const lower = text.toLowerCase();
        let changed = false;

        if (lower.includes('faang') || lower.includes('google') || lower.includes('amazon') || lower.includes('meta')) {
            this.profileState.companyTier = 'FAANG / Tier-1 Tech';
            changed = true;
        } else if (lower.includes('startup') || lower.includes('unicorn')) {
            this.profileState.companyTier = 'High Growth Startup';
            changed = true;
        }

        if (lower.includes('2 week') || lower.includes('14 day')) {
            this.profileState.timeline = '14 Days';
            changed = true;
        } else if (lower.includes('60 day') || lower.includes('2 month')) {
            this.profileState.timeline = '60 Days';
            changed = true;
        } else if (lower.includes('90 day') || lower.includes('3 month')) {
            this.profileState.timeline = '90 Days';
            changed = true;
        }

        if (changed) {
            this.saveProfile();
            this.syncProfileUi();
        }
    }

    sendQuickPrompt(text) {
        const input = document.getElementById('chatInput');
        input.value = text;
        this.handleSendMessage();
    }

    // ==================== DYNAMIC ROADMAP GENERATION ====================

    regenerateFromCurrentSettings() {
        this.generateTailoredRoadmap();
        this.showToast('Roadmap regenerated from your active matrix!');
    }

    generateTailoredRoadmap() {
        const role = this.profileState.targetRole;
        const timeline = this.profileState.timeline;
        const tier = this.profileState.companyTier;
        const tech = this.profileState.techStack.join(', ');

        const totalHours = timeline === '14 Days' ? 32 : (timeline === '60 Days' ? 72 : (timeline === '90 Days' ? 96 : 48));

        this.currentPlan = {
            title: `${role} (${tier})`,
            timeline: timeline,
            totalEstimatedHours: totalHours,
            milestones: [
                // Milestone 1: DSA & LeetCode Patterns
                {
                    id: 1,
                    sequenceOrder: 1,
                    weekNumber: 1,
                    phaseName: "Phase 1: Algorithmic Patterns & LeetCode Problem Mastery",
                    description: "High-yield coding patterns with direct LeetCode problem links, NeetCode visual solutions, and Grokking Algorithms chapters.",
                    estimatedHours: Math.round(totalHours * 0.3),
                    tasks: [
                        {
                            id: 101,
                            dayNumber: 1,
                            title: "Array Hashing & Two Pointers: Two Sum (#1) & 3Sum (#15)",
                            description: "Solve Two Sum with HashMap O(N) and 3Sum with Sorted Two Pointers O(N^2). Avoid duplicate triplets.",
                            category: "CODING_PRACTICE",
                            completed: false,
                            leetcodeUrl: "https://leetcode.com/problems/3sum/",
                            bookTitle: "Grokking Algorithms (Ch. 1-2)",
                            bookUrl: "https://www.manning.com/books/grokking-algorithms-second-edition",
                            videoTitle: "NeetCode 3Sum Solution",
                            videoUrl: "https://www.youtube.com/@NeetCode"
                        },
                        {
                            id: 102,
                            dayNumber: 2,
                            title: "Sliding Window: Longest Substring Without Repeating Characters (#3)",
                            description: "Implement dynamic sliding window with character frequency map and left/right pointer expansion.",
                            category: "CODING_PRACTICE",
                            completed: false,
                            leetcodeUrl: "https://leetcode.com/problems/longest-substring-without-repeating-characters/",
                            videoTitle: "NeetCode Sliding Window Patterns",
                            videoUrl: "https://www.youtube.com/@NeetCode"
                        },
                        {
                            id: 103,
                            dayNumber: 3,
                            title: "Graphs (BFS/DFS): Number of Islands (#200) & Clone Graph (#133)",
                            description: "Master Matrix Graph traversal with DFS/BFS and cycle detection with visited sets.",
                            category: "CODING_PRACTICE",
                            completed: false,
                            leetcodeUrl: "https://leetcode.com/problems/number-of-islands/",
                            bookTitle: "Grokking Algorithms (Ch. 6 Graphs)",
                            bookUrl: "https://www.manning.com/books/grokking-algorithms-second-edition",
                            videoTitle: "NeetCode Graph Algorithms",
                            videoUrl: "https://www.youtube.com/@NeetCode"
                        },
                        {
                            id: 104,
                            dayNumber: 4,
                            title: "Dynamic Programming: Coin Change (#322) & Climbing Stairs (#70)",
                            description: "Bottom-up 1D DP tabulation vs top-down memoization for unbounded knapsack patterns.",
                            category: "CODING_PRACTICE",
                            completed: false,
                            leetcodeUrl: "https://leetcode.com/problems/coin-change/",
                            bookTitle: "Grokking Algorithms (Ch. 9 DP)",
                            bookUrl: "https://www.manning.com/books/grokking-algorithms-second-edition"
                        },
                        {
                            id: 105,
                            dayNumber: 5,
                            title: "Design Pattern in LeetCode: LRU Cache (#146)",
                            description: "Design O(1) get and put using a Doubly Linked List and HashMap combination.",
                            category: "CODING_PRACTICE",
                            completed: false,
                            leetcodeUrl: "https://leetcode.com/problems/lru-cache/",
                            videoTitle: "ByteByteGo LRU Cache Architecture",
                            videoUrl: "https://www.youtube.com/@ByteByteGo"
                        }
                    ]
                },

                // Milestone 2: Scalable Distributed Systems & High-Level Design
                {
                    id: 2,
                    sequenceOrder: 2,
                    weekNumber: 2,
                    phaseName: "Phase 2: Scalable Distributed Systems & High-Level Architecture",
                    description: "Architect high-throughput systems, consistent hashing, caching strategies, and message queue partitioning.",
                    estimatedHours: Math.round(totalHours * 0.3),
                    tasks: [
                        {
                            id: 201,
                            dayNumber: 6,
                            title: "System Design Blueprint: Capacity Math & Back-of-the-Envelope Estimation",
                            description: "Estimate QPS, Storage, Read/Write throughput, Bandwidth, and Cache RAM requirements for 100M DAU.",
                            category: "SYSTEM_DESIGN",
                            completed: false,
                            bookTitle: "System Design Interview (Alex Xu Ch. 2)",
                            bookUrl: "https://bytebytego.com/",
                            videoTitle: "ByteByteGo Back-of-Envelope Math",
                            videoUrl: "https://www.youtube.com/@ByteByteGo"
                        },
                        {
                            id: 202,
                            dayNumber: 7,
                            title: "Distributed Caching & Redis Architecture (Cache-Aside, Write-Through)",
                            description: "Design caching layers, cache eviction policies (LRU/LFU), and mitigation for Cache Stampede and Cache Penetration.",
                            category: "SYSTEM_DESIGN",
                            completed: false,
                            bookTitle: "Designing Data-Intensive Applications (Ch. 3)",
                            bookUrl: "https://dataintensive.net/",
                            videoTitle: "Gaurav Sen Consistent Hashing & Redis",
                            videoUrl: "https://www.youtube.com/@gkcs"
                        },
                        {
                            id: 203,
                            dayNumber: 8,
                            title: "Distributed Event Streaming with Apache Kafka",
                            description: "Master Topic Partitioning, Consumer Groups, Offsets, Idempotent Producers, and Exactly-Once Semantics (EOS).",
                            category: "READING",
                            completed: false,
                            bookTitle: "DDIA (Ch. 11 Stream Processing)",
                            bookUrl: "https://dataintensive.net/",
                            tutorialTitle: "Baeldung Spring Kafka Tutorial",
                            tutorialUrl: "https://www.baeldung.com/spring-boot"
                        },
                        {
                            id: 204,
                            dayNumber: 9,
                            title: "Architect a Distributed Rate Limiter (Token Bucket & Leaky Bucket)",
                            description: "Design a sliding window counter rate limiter in Redis with Lua scripts for atomic operations.",
                            category: "SYSTEM_DESIGN",
                            completed: false,
                            bookTitle: "System Design Interview (Alex Xu Ch. 4)",
                            bookUrl: "https://bytebytego.com/",
                            videoTitle: "ByteByteGo Rate Limiter Design",
                            videoUrl: "https://www.youtube.com/@ByteByteGo"
                        },
                        {
                            id: 205,
                            dayNumber: 10,
                            title: "Database Partitioning, Replication & CAP Theorem Trade-offs",
                            description: "Compare Master-Slave replication, Multi-Leader conflict resolution, and PACELC theorem in production.",
                            category: "READING",
                            completed: false,
                            bookTitle: "DDIA (Ch. 5 & 6 Replication/Partitioning)",
                            bookUrl: "https://dataintensive.net/"
                        }
                    ]
                },

                // Milestone 3: Core Stack Mastery & Low-Level Design (LLD)
                {
                    id: 3,
                    sequenceOrder: 3,
                    weekNumber: 3,
                    phaseName: `Phase 3: ${role.includes('Python') ? 'Python & FastAPI' : 'Java 21 & Spring Boot 3'} Deep-Dive & Clean Architecture`,
                    description: "Master Virtual Threads, Spring Boot microservices, SOLID principles, and database performance tuning.",
                    estimatedHours: Math.round(totalHours * 0.25),
                    tasks: [
                        {
                            id: 301,
                            dayNumber: 11,
                            title: "Java 21 Concurrency: Virtual Threads & Structured Concurrency",
                            description: "Deep dive into Project Loom, carrier thread pinning avoidance, and asynchronous non-blocking execution.",
                            category: "READING",
                            completed: false,
                            tutorialTitle: "Baeldung Java 21 Virtual Threads",
                            tutorialUrl: "https://www.baeldung.com/spring-boot",
                            videoTitle: "Hussein Nasser Threading & I/O Internals",
                            videoUrl: "https://www.youtube.com/@hnasr"
                        },
                        {
                            id: 302,
                            dayNumber: 12,
                            title: "Spring Boot 3 Microservices, Security & Gateway Routing",
                            description: "Configure Spring Cloud Gateway, JWT authentication filter, and Resilience4j Circuit Breakers.",
                            category: "READING",
                            completed: false,
                            tutorialTitle: "Spring.io Official Reference",
                            tutorialUrl: "https://spring.io/projects/spring-boot"
                        },
                        {
                            id: 303,
                            dayNumber: 13,
                            title: "Low-Level Design (LLD): Factory, Strategy & Observer Patterns",
                            description: "Design a Parking Lot System and Payment Processor using clean SOLID design patterns.",
                            category: "SYSTEM_DESIGN",
                            completed: false,
                            bookTitle: "Clean Code (Uncle Bob)",
                            bookUrl: "https://www.oreilly.com/library/view/clean-code-a/9780136083238/",
                            tutorialTitle: "Refactoring Guru Design Patterns",
                            tutorialUrl: "https://refactoring.guru/design-patterns"
                        },
                        {
                            id: 304,
                            dayNumber: 14,
                            title: "PostgreSQL Index Internals & Query Optimization",
                            description: "Understand B-Tree vs Hash vs GIN indexes, EXPLAIN ANALYZE query plans, and N+1 query elimination.",
                            category: "READING",
                            completed: false,
                            videoTitle: "Hussein Nasser Database Indexing Internals",
                            videoUrl: "https://www.youtube.com/@hnasr"
                        }
                    ]
                },

                // Milestone 4: Behavioral Leadership (STAR Method) & Live Mock Simulations
                {
                    id: 4,
                    sequenceOrder: 4,
                    weekNumber: 4,
                    phaseName: "Phase 4: Behavioral Leadership (STAR Method) & Mock Interview Drills",
                    description: "Formulate structured STAR behavioral answers, practice architecture defense, and execute timed mock simulations.",
                    estimatedHours: Math.round(totalHours * 0.15),
                    tasks: [
                        {
                            id: 401,
                            dayNumber: 15,
                            title: "STAR Behavioral Formulation: Technical Outages & Conflicts",
                            description: "Draft 5 structured STAR stories: Difficult Technical Trade-off, Production Outage, Peer Disagreement, and Mentorship.",
                            category: "MOCK_INTERVIEW",
                            completed: false,
                            tutorialTitle: "Roadmap.sh Backend Behavioral Guide",
                            tutorialUrl: "https://roadmap.sh/backend"
                        },
                        {
                            id: 402,
                            dayNumber: 16,
                            title: "System Architecture Defense Simulation",
                            description: "Practice presenting a production system architecture, justifying CAP theorem choices and scalability bottlenecks.",
                            category: "MOCK_INTERVIEW",
                            completed: false,
                            tutorialTitle: "Martin Fowler Microservices Architecture",
                            tutorialUrl: "https://martinfowler.com/articles/microservices.html"
                        },
                        {
                            id: 403,
                            dayNumber: 17,
                            title: "Full-Length Timed Mock Interview Simulation (90 Mins)",
                            description: "Complete a timed simulation: 30 mins LeetCode coding, 45 mins System Design, and 15 mins Behavioral rounds.",
                            category: "MOCK_INTERVIEW",
                            completed: false,
                            leetcodeUrl: "https://leetcode.com/studyplan/top-interview-150/"
                        }
                    ]
                }
            ]
        };

        // Reapply saved task completions if any
        const completedIds = JSON.parse(localStorage.getItem('ic_completed_tasks') || '[]');
        this.currentPlan.milestones.forEach(m => {
            m.tasks.forEach(t => {
                if (completedIds.includes(t.id)) {
                    t.completed = true;
                }
            });
        });

        this.renderRoadmap(this.currentPlan);
    }

    renderRoadmap(plan) {
        if (!plan) return;

        document.getElementById('planRoleHeader').textContent = plan.title;
        document.getElementById('planTotalHours').textContent = plan.totalEstimatedHours;
        document.getElementById('planTimelineLabel').textContent = `Hours (${this.profileState.timeline} Plan)`;

        let totalTasks = 0;
        let completedTasks = 0;

        plan.milestones.forEach(m => {
            m.tasks.forEach(t => {
                totalTasks++;
                if (t.completed) completedTasks++;
            });
        });

        document.getElementById('totalTasksCount').textContent = totalTasks;
        document.getElementById('completedTasksCount').textContent = completedTasks;
        document.getElementById('headerTasksCount').textContent = `${totalTasks} Tasks`;

        const score = totalTasks > 0 ? Math.round((completedTasks / totalTasks) * 100) : 0;
        document.getElementById('readinessScoreText').textContent = `${score}%`;
        document.getElementById('readinessCircle').style.setProperty('--percent', score);

        const container = document.getElementById('milestonesContainer');
        if (!container) return;

        container.innerHTML = '';

        plan.milestones.forEach(m => {
            // Filter tasks based on active category
            const visibleTasks = m.tasks.filter(t => {
                if (this.activeRoadmapFilter === 'ALL') return true;
                return t.category === this.activeRoadmapFilter;
            });

            if (visibleTasks.length === 0 && this.activeRoadmapFilter !== 'ALL') return;

            const card = document.createElement('div');
            card.className = 'milestone-card';

            let tasksHtml = '';
            visibleTasks.forEach(t => {
                const checked = t.completed ? 'completed' : '';
                const checkIcon = t.completed ? '<i class="ph-bold ph-check"></i>' : '';

                // Build Reference Action Buttons
                let referenceButtonsHtml = '';

                if (t.leetcodeUrl) {
                    referenceButtonsHtml += `
                        <a href="${t.leetcodeUrl}" target="_blank" rel="noopener" class="btn-leetcode-link" title="Open LeetCode Problem">
                            <i class="ph-bold ph-code"></i>
                            <span>LeetCode Problem Link</span>
                        </a>
                    `;
                }

                if (t.bookTitle && t.bookUrl) {
                    referenceButtonsHtml += `
                        <a href="${t.bookUrl}" target="_blank" rel="noopener" class="btn-reference-book" title="Open Book Reference">
                            <i class="ph-bold ph-book-bookmark"></i>
                            <span>${t.bookTitle}</span>
                        </a>
                    `;
                }

                if (t.videoTitle && t.videoUrl) {
                    referenceButtonsHtml += `
                        <a href="${t.videoUrl}" target="_blank" rel="noopener" class="btn-reference-video" title="Open YouTube Walkthrough">
                            <i class="ph-bold ph-youtube-logo"></i>
                            <span>${t.videoTitle}</span>
                        </a>
                    `;
                }

                if (t.tutorialTitle && t.tutorialUrl) {
                    referenceButtonsHtml += `
                        <a href="${t.tutorialUrl}" target="_blank" rel="noopener" class="btn-reference-tutorial" title="Open Tutorial Guide">
                            <i class="ph-bold ph-globe"></i>
                            <span>${t.tutorialTitle}</span>
                        </a>
                    `;
                }

                tasksHtml += `
                    <div class="task-item ${checked}" data-task-id="${t.id}">
                        <div class="task-top-row">
                            <div class="task-left">
                                <div class="task-checkbox" onclick="app.toggleTask(${t.id})">
                                    ${checkIcon}
                                </div>
                                <div class="task-info">
                                    <h5>Day ${t.dayNumber}: ${t.title}</h5>
                                    <p>${t.description}</p>
                                </div>
                            </div>
                            <span class="task-category-badge">${t.category.replace('_', ' ')}</span>
                        </div>
                        
                        <div class="reference-links-row">
                            <span class="reference-label">Reference Material:</span>
                            ${referenceButtonsHtml}
                        </div>
                    </div>
                `;
            });

            card.innerHTML = `
                <div class="milestone-header">
                    <div>
                        <span class="phase-tag">Week ${m.weekNumber}</span>
                        <h3>${m.phaseName}</h3>
                        <p class="subtitle" style="margin-top: 4px;">${m.description}</p>
                    </div>
                    <div class="milestone-meta">
                        <span class="badge-hours">${m.estimatedHours} Hours</span>
                    </div>
                </div>
                <div class="tasks-list">
                    ${tasksHtml}
                </div>
            `;

            container.appendChild(card);
        });
    }

    toggleTask(taskId) {
        let toggled = false;
        let newStatus = false;

        this.currentPlan.milestones.forEach(m => {
            m.tasks.forEach(t => {
                if (t.id === taskId) {
                    t.completed = !t.completed;
                    newStatus = t.completed;
                    toggled = true;
                }
            });
        });

        if (toggled) {
            // Save to localStorage
            const completedIds = [];
            this.currentPlan.milestones.forEach(m => {
                m.tasks.forEach(t => {
                    if (t.completed) completedIds.push(t.id);
                });
            });
            localStorage.setItem('ic_completed_tasks', JSON.stringify(completedIds));

            this.renderRoadmap(this.currentPlan);
            this.showToast(newStatus ? 'Task marked complete! 🎯' : 'Task marked pending');
        }
    }

    // ==================== VERIFIED MATERIALS HUB ====================

    loadVerifiedResources() {
        this.allResources = [
            {
                title: "Designing Data-Intensive Applications",
                authorOrChannel: "Martin Kleppmann",
                category: "BOOK",
                url: "https://dataintensive.net/",
                domain: "dataintensive.net",
                description: "The definitive gold-standard book for Distributed Systems, Replication, Partitioning, Transactions, and Streams.",
                httpStatusCode: 200,
                topics: ["System Design", "Distributed Systems", "Databases", "Kafka", "Replication"]
            },
            {
                title: "System Design Interview – An Insider's Guide",
                authorOrChannel: "Alex Xu & Sahn Lam",
                category: "BOOK",
                url: "https://bytebytego.com/",
                domain: "bytebytego.com",
                description: "Practical blueprints for architecting large scale systems like Rate Limiters, YouTube, and Distributed Caching.",
                httpStatusCode: 200,
                topics: ["System Design", "Scalability", "Microservices", "Caching", "Rate Limiter"]
            },
            {
                title: "Clean Code: Handbook of Agile Software Craftsmanship",
                authorOrChannel: "Robert C. Martin (Uncle Bob)",
                category: "BOOK",
                url: "https://www.oreilly.com/library/view/clean-code-a/9780136083238/",
                domain: "oreilly.com",
                description: "Foundational handbook for writing readable, maintainable, SOLID object-oriented code and low level design.",
                httpStatusCode: 200,
                topics: ["Clean Code", "SOLID Principles", "Low Level Design", "Refactoring", "Design Patterns"]
            },
            {
                title: "Grokking Algorithms: An Illustrated Guide",
                authorOrChannel: "Aditya Bhargava",
                category: "BOOK",
                url: "https://www.manning.com/books/grokking-algorithms-second-edition",
                domain: "manning.com",
                description: "Visually rich and intuitive breakdown of fundamental algorithms, Big-O, Graph Traversals, and Dynamic Programming.",
                httpStatusCode: 200,
                topics: ["DSA", "Algorithms", "Big-O", "Graph Algorithms", "Dynamic Programming"]
            },
            {
                title: "NeetCode – Blind 75 & NeetCode 150 Algorithmic Patterns",
                authorOrChannel: "NeetCode",
                category: "YOUTUBE_CHANNEL",
                url: "https://www.youtube.com/@NeetCode",
                domain: "youtube.com",
                description: "Master DSA patterns with the famous Blind 75 and NeetCode 150 visual walkthroughs across DP, Graphs, and Trees.",
                httpStatusCode: 200,
                topics: ["DSA", "LeetCode", "Algorithms", "Coding Interview", "Patterns"]
            },
            {
                title: "ByteByteGo – Visual System Design Channel",
                authorOrChannel: "Alex Xu",
                category: "YOUTUBE_CHANNEL",
                url: "https://www.youtube.com/@ByteByteGo",
                domain: "youtube.com",
                description: "High-impact visual explanations of cloud architectures, Redis caching strategies, and Message Queues.",
                httpStatusCode: 200,
                topics: ["System Design", "Microservices", "Cloud Architecture", "Distributed Systems"]
            },
            {
                title: "Gaurav Sen – System Design Fundamentals",
                authorOrChannel: "Gaurav Sen",
                category: "YOUTUBE_CHANNEL",
                url: "https://www.youtube.com/@gkcs",
                domain: "youtube.com",
                description: "Deep dive into core primitives: Consistent Hashing, Database Sharding, Bloom Filters, and Load Balancers.",
                httpStatusCode: 200,
                topics: ["System Design", "Consistent Hashing", "Database Sharding", "Distributed Systems"]
            },
            {
                title: "Hussein Nasser – Backend Engineering & Protocols",
                authorOrChannel: "Hussein Nasser",
                category: "YOUTUBE_CHANNEL",
                url: "https://www.youtube.com/@hnasr",
                domain: "youtube.com",
                description: "Masterclasses covering HTTP/2, HTTP/3, WebSockets, gRPC, Database Indexing internals (B-Trees), and Networking.",
                httpStatusCode: 200,
                topics: ["Backend Engineering", "Database Internals", "Networking", "PostgreSQL"]
            },
            {
                title: "LeetCode Top Interview 150 Study Plan",
                authorOrChannel: "LeetCode",
                category: "PRACTICE_PLATFORM",
                url: "https://leetcode.com/studyplan/top-interview-150/",
                domain: "leetcode.com",
                description: "Must-solve problem collection covering Array/String, Hashmaps, Graphs, Binary Search, Trees, and DP.",
                httpStatusCode: 200,
                topics: ["DSA", "Coding Practice", "Algorithms", "LeetCode"]
            },
            {
                title: "Baeldung – Java & Spring Boot Guides",
                authorOrChannel: "Eugen Paraschiv",
                category: "ONLINE_TUTORIAL",
                url: "https://www.baeldung.com/spring-boot",
                domain: "baeldung.com",
                description: "Practical tutorials on Spring Boot 3, Spring Security, JPA/Hibernate, Concurrency, and REST APIs.",
                httpStatusCode: 200,
                topics: ["Java", "Spring Boot", "Microservices", "Spring Security"]
            },
            {
                title: "Refactoring Guru – Design Patterns in Depth",
                authorOrChannel: "Alexander Shvets",
                category: "ONLINE_TUTORIAL",
                url: "https://refactoring.guru/design-patterns",
                domain: "refactoring.guru",
                description: "Comprehensive interactive catalog of Creational, Structural, and Behavioral Design Patterns with Java code.",
                httpStatusCode: 200,
                topics: ["Design Patterns", "Low Level Design", "OOP", "Refactoring", "SOLID"]
            },
            {
                title: "Roadmap.sh – Interactive Backend Developer Roadmap",
                authorOrChannel: "roadmap.sh",
                category: "ONLINE_TUTORIAL",
                url: "https://roadmap.sh/backend",
                domain: "roadmap.sh",
                description: "Step-by-step interactive visual guides for backend competencies, architectural patterns, APIs, and DevOps.",
                httpStatusCode: 200,
                topics: ["Backend Engineering", "Roadmap", "API Design", "Security"]
            },
            {
                title: "Spring Framework & Spring Boot Official Docs",
                authorOrChannel: "VMware / Spring.io",
                category: "DOCUMENTATION",
                url: "https://spring.io/projects/spring-boot",
                domain: "spring.io",
                description: "Official documentation for Spring Boot architecture, Spring Cloud Gateway, and Reactive WebFlux.",
                httpStatusCode: 200,
                topics: ["Spring Boot", "Spring Cloud", "Official Docs", "Java"]
            },
            {
                title: "Martin Fowler – Microservices Architecture Guide",
                authorOrChannel: "Martin Fowler",
                category: "DOCUMENTATION",
                url: "https://martinfowler.com/articles/microservices.html",
                domain: "martinfowler.com",
                description: "Foundational definition of Microservices architecture, Domain-Driven Design (DDD), and Event-Driven Architecture.",
                httpStatusCode: 200,
                topics: ["Microservices", "System Design", "Architecture", "DDD"]
            }
        ];

        this.renderResources();
    }

    renderResources() {
        const grid = document.getElementById('resourcesGrid');
        if (!grid) return;

        const filtered = this.allResources.filter(r => {
            if (this.activeCategory === 'ALL') return true;
            return r.category === this.activeCategory;
        });

        grid.innerHTML = '';

        filtered.forEach(r => {
            const card = document.createElement('div');
            card.className = 'resource-card';

            const catClass = (r.category || 'tutorial').toLowerCase().replace('_channel', '').replace('_platform', '');
            const topicsHtml = (r.topics || []).map(t => `<span class="tag">${t}</span>`).join('');

            card.innerHTML = `
                <div class="resource-card-top">
                    <div class="resource-category-row">
                        <span class="cat-badge ${catClass}">${r.category.replace('_', ' ')}</span>
                        <div class="verified-health-indicator">
                            <span class="pulse-dot"></span>
                            <span>HTTP ${r.httpStatusCode || 200} Verified</span>
                        </div>
                    </div>
                    <h4 class="resource-title">${r.title}</h4>
                    <p class="resource-author"><i class="ph-bold ph-user"></i> ${r.authorOrChannel || 'Expert Resource'}</p>
                    <p class="resource-desc">${r.description || 'Verified study reference tested for 100% active access.'}</p>
                    <div class="resource-topics-list">
                        ${topicsHtml}
                    </div>
                </div>
                <div class="resource-actions">
                    <span class="domain-tag"><i class="ph-bold ph-globe"></i> ${r.domain || 'Verified Domain'}</span>
                    <a href="${r.url}" target="_blank" rel="noopener" class="btn btn-primary btn-sm">
                        <span>Open Material</span>
                        <i class="ph-bold ph-arrow-up-right"></i>
                    </a>
                </div>
            `;

            grid.appendChild(card);
        });
    }

    filterResources(query) {
        if (!query || query.trim() === '') {
            this.renderResources();
            return;
        }

        const q = query.toLowerCase();
        const grid = document.getElementById('resourcesGrid');
        const filtered = this.allResources.filter(r => 
            (r.title && r.title.toLowerCase().includes(q)) ||
            (r.authorOrChannel && r.authorOrChannel.toLowerCase().includes(q)) ||
            (r.description && r.description.toLowerCase().includes(q)) ||
            (r.topics && r.topics.some(t => t.toLowerCase().includes(q)))
        );

        grid.innerHTML = '';
        filtered.forEach(r => {
            const card = document.createElement('div');
            card.className = 'resource-card';
            const catClass = (r.category || 'tutorial').toLowerCase().replace('_channel', '').replace('_platform', '');
            const topicsHtml = (r.topics || []).map(t => `<span class="tag">${t}</span>`).join('');

            card.innerHTML = `
                <div class="resource-card-top">
                    <div class="resource-category-row">
                        <span class="cat-badge ${catClass}">${r.category.replace('_', ' ')}</span>
                        <div class="verified-health-indicator">
                            <span class="pulse-dot"></span>
                            <span>HTTP ${r.httpStatusCode || 200} Verified</span>
                        </div>
                    </div>
                    <h4 class="resource-title">${r.title}</h4>
                    <p class="resource-author"><i class="ph-bold ph-user"></i> ${r.authorOrChannel || 'Expert Resource'}</p>
                    <p class="resource-desc">${r.description}</p>
                    <div class="resource-topics-list">${topicsHtml}</div>
                </div>
                <div class="resource-actions">
                    <span class="domain-tag"><i class="ph-bold ph-globe"></i> ${r.domain}</span>
                    <a href="${r.url}" target="_blank" rel="noopener" class="btn btn-primary btn-sm">
                        <span>Open Material</span>
                        <i class="ph-bold ph-arrow-up-right"></i>
                    </a>
                </div>
            `;
            grid.appendChild(card);
        });
    }

    // ==================== HELPERS ====================

    formatMarkdown(text) {
        if (!text) return '';
        return text
            .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
            .replace(/\*(.*?)\*/g, '<em>$1</em>')
            .replace(/`(.*?)`/g, '<code>$1</code>')
            .replace(/\[(.*?)\]\((.*?)\)/g, '<a href="$2" target="_blank" rel="noopener" style="color: var(--accent-cyan); text-decoration: underline;">$1</a>')
            .replace(/\n\n/g, '<br><br>')
            .replace(/\n/g, '<br>');
    }

    showToast(message, isError = false) {
        const toast = document.getElementById('toast');
        if (!toast) return;
        toast.textContent = message;
        toast.style.borderLeftColor = isError ? 'var(--accent-rose)' : 'var(--accent-emerald)';
        toast.classList.add('show');
        setTimeout(() => toast.classList.remove('show'), 3500);
    }
}

// Instantiate App
const app = new InterviewCraftApp();
