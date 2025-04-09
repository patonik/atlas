<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <div>
        <label for="userCode">Username:</label>
        <input v-model="userCode" id="userCode" type="text" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input v-model="password" id="password" type="password" required />
      </div>
      <button type="submit">Login</button>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';

const userCode = ref('');
const password = ref('');
const error = ref('');
const router = useRouter();
const authStore = useAuthStore();

const handleLogin = async () => {
  try {
    // Log the request payload for debugging
    console.log('Sending login request:', {
      userCode: userCode.value,
      userPassword: password.value,
      systemSite: 'HMCIS',
    });
    const response = await axios.post(
        'http://localhost:9090/api/login',
        {
          userCode: userCode.value,
          userPassword: password.value,
          systemSite: 'HMCIS',
        },
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
    );

    // Log the full response for debugging
    console.log('Response status:', response.status);
    console.log('Response headers:', response.headers);
    console.log('Response data:', response.data);

    // Extract token from Authorization header
    const token = response.headers['authorization'];
    if (token) {
      console.log('Token extracted:', token);
      authStore.setToken(token);
      router.push('/dashboard');
    } else {
      console.warn('No token found in response headers');
      error.value = 'Login failed: No token received';
    }
  } catch (err) {
    // Log detailed error info
    console.error('Login request failed:', {
      status: err.response?.status,
      headers: err.response?.headers,
      data: err.response?.data,
      message: err.message,
    });
    error.value = err.response?.data?.message || 'Login failed: Server error';
  }
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
div {
  margin-bottom: 15px;
}
label {
  display: block;
  margin-bottom: 5px;
}
input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
.error {
  color: red;
  margin-top: 10px;
}
</style>