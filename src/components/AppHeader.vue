<template>
  <div class="zoomInUp">
    <ul class="navbar-nav">
      <a href="/home">
        <img src="/src/assets/logo.png" alt="Home" class="nav-icon">
      </a> 
      <li>
        <a href="/home" class="nav-link btn">Home</a>
      </li>
      <li v-if="hasRole('OWNER')">
        <a href="/newProperty" class="nav-link btn">Insert Property</a>
      </li>
      <li v-if="hasRole('OWNER')">
        <a href="/getRequests" class="nav-link btn">Pending Requests</a>
      </li>
      <li v-if="hasRole('OWNER')">
        <a href="/oprofile" class="nav-link btn">My Profile</a>
      </li>
      <li v-if="hasRole('TENANT')">
        <a href="/search" class="nav-link btn">Search Property</a>
      </li>
      <li v-if="hasRole('TENANT')">
        <a href="/notifications" class="nav-link btn">My Applications</a>
      </li>
      <li v-if="hasRole('TENANT')">
        <a href="/profile" class="nav-link btn">My Profile</a>
      </li>
      <li v-if="hasRole('ADMIN')">
        <a href="/users" class="nav-link btn">Pending Users</a>
      </li>
      <li v-if="hasRole('ADMIN')">
        <a href="/applicationManagement" class="nav-link btn">Properties & Requests</a>
      </li>
      <li v-if="isAuthenticated">
        <a href="#" @click.prevent="handleLogout" class="nav-link btn logout-btn">Sign Out</a>
      </li>
      <li v-if="!isAuthenticated">
        <a href="/login" class="nav-link btn login-btn">Sign In</a>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';

const { isAuthenticated, loadUserData, logout } = useApplicationStore();
const userData = loadUserData();
const router = useRouter();
const isHomePage = ref(false);

const handleLogout = () => {
  logout();
};

const hasRole = (role) => {
  return isAuthenticated && userData.roles[0].includes(role);
};

router.beforeEach((to, from, next) => {
  isHomePage.value = to.name === 'home';
  next();
});
</script>

<style scoped>
.navbar-nav {
  list-style: none;
  padding: 10px 0;
  margin: 0;
  display: flex;
  justify-content: center;
  gap: 2px;
  flex-wrap: wrap;
  background-color: #8d614c;
  /* background: linear-gradient(to right, #8e624d, #7b5e49, #8d614c); */
  border-radius: 0; 
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1000; 
}

.btn {
  text-decoration: none;
  padding: 40px 20px;
  margin: 0;
  font-size: 16px;
  font-weight: bold;
  color: white;
  background:#5a0b0b;
  border-radius: 8px;
  transition: all 0.3s ease;
  display: inline-block;
  text-align: center;
  border: none;
  cursor: pointer;
}

.zoomInUp {
  animation: zoomInUp 1s ease forwards;
  margin: 0;
}

.header-box {
  margin: 0;
}

.btn:hover {
  background: #dbc641;
  box-shadow: 0 0 12px rgba(80, 173, 240, 0.5);
}

.logout-btn {
  background: linear-gradient(to right, #ff4b4b, #d32f2f);
}

.logout-btn:hover {
  background: linear-gradient(to right, #e53935, #b71c1c);
}

.login-btn {
  background: linear-gradient(to right, #3498db, #2980b9);
}

.login-btn:hover {
  background: linear-gradient(to right, #2980b9, #1c6ea4);
}

img {
  width: 20%;
  margin: 0;
  padding: 0;
}

@media (max-width: 768px) {
  .navbar-nav {
    flex-direction: column;
    align-items: center;
  }

  .btn {
    width: 80%;
    text-align: center;
  }
}
</style>