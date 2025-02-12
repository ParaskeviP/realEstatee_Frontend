<template>
    <div class="header-box" :class="{ 'is-homepage': isHomePage }">
      <div class="zoomInUp">
        <a href="/home">
          <img src="/src/assets/logo.png" alt="Home" class="nav-icon">
        </a>  
      <ul class="navbar-nav">
        <li v-if="hasRole('OWNER')">
          <a href="/newProperty" class="nav-link btn">Add Property</a>
        </li>
        <li v-if="hasRole('OWNER')">
          <a href="/getRequests" class="nav-link btn">Show Requests</a>
        </li>
        <li v-if="hasRole('TENANT')">
          <a href="/showProperties" class="nav-link btn">See all Properties</a>
        </li>
        <li v-if="hasRole('TENANT')">
          <a href="/searchProperties" class="nav-link btn">Search Properties</a>
        </li>
        <li v-if="hasRole('ADMIN')">
          <a href="/users" class="nav-link btn">Users</a>
        </li>
        <li v-if="isAuthenticated">
          <a href="#" @click.prevent="handleLogout" class="nav-link btn logout-btn">Log Out</a>
        </li>
        <li v-if="!isAuthenticated">
          <a href="/login" class="nav-link btn login-btn">Login</a>
        </li>
      </ul>
    </div>
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
  padding: 0;
  display: flex;
  justify-content: center;
  gap: 15px;
  flex-wrap: wrap;
}

.btn {
  text-decoration: none;
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
  color: white;
  background: linear-gradient(to right, #646665, #2e3030, #2a2828);
  border-radius: 8px;
  transition: all 0.3s ease;
  display: inline-block;
  text-align: center;
  border: none;
  cursor: pointer;
}

.zoomInUp {
  animation: zoomInUp 1s ease forwards;
  align-items: center;
}

.btn:hover {
  background: linear-gradient(to right, #4e5050, #343737, #2a2828);
  box-shadow: 0 0 12px rgba(80, 173, 240, 0.5);
}

/* Different colors for Logout and Login buttons */
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
  align-content: center;
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