from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
import httpx
import os

app = FastAPI(title="AgriPulse Bhashini AI Voice Bridge", version="3.0")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class VoiceRequest(BaseModel):
    audio_base64: str
    language_code: str = "hi"
    user_email: str = "farmer@agripulse.com"

class ChatTextRequest(BaseModel):
    message: str
    language: str = "hi"

SPRING_BOOT_AI_URL = os.getenv("SPRING_BOOT_URL", "http://localhost:8080/api/ai/chat")

@app.get("/health")
def health_check():
    return {"status": "active", "service": "Bhashini Voice Bridge"}

@app.post("/api/voice/process")
async def process_voice_interaction(req: VoiceRequest):
    """
    Simulates / integrates Bhashini STT -> Spring Boot AI -> Bhashini TTS.
    In production, this calls Bhashini pipeline APIs. Here we provide robust fallback & simulation.
    """
    try:
        # 1. Simulate Speech-to-Text translation from regional audio
        transcribed_text = "Namaste, what are the best fertilizer recommendations for wheat in Mathura?"
        
        # 2. Forward transcript to Spring Boot backend AI endpoint
        async with httpx.AsyncClient() as client:
            ai_response = await client.post(
                SPRING_BOOT_AI_URL,
                json={"message": transcribed_text, "language": req.language_code},
                timeout=15.0
            )
            
            ai_text = "Ensure timely irrigation and balanced nitrogen fertilizer application."
            if ai_response.status_code == 200:
                data = ai_response.json()
                ai_text = data.get("response", ai_text)

        # 3. Simulate Text-to-Speech audio generation (returning mock base64 audio payload)
        return {
            "transcript": transcribed_text,
            "ai_response": ai_text,
            "audio_response_base64": "UklGRiQAAABXQVZFZm10IBAAAAABAAEAQB8AAEAfAAABAAgAZGF0YQAAAAA=",
            "language": req.language_code
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str.startswith(str(e), "Error") and str(e) or f"Voice bridge error: {str(e)}")