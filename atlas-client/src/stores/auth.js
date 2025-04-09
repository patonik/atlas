import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useAuthStore = defineStore('auth', () => {
    const token = ref(null);

    function setToken(newToken) {
        token.value = newToken;
    }

    function clearToken() {
        token.value = null;
    }

    return { token, setToken, clearToken };
});