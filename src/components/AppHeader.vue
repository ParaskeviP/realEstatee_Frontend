<template>
  <div class="header-box" :class="{ 'is-homepage': isHomePage }">
    <div class="zoomInUp">
      <ul class="navbar-nav">
        <li>
          <a href="/home" class="nav-link box">Home</a>
        </li>
        <li v-if="hasRole('OWNER')">
          <a href="/newProperty" class="nav-link box">Add Property</a>
          <a href="/getRequests" class="nav-link box">Show Requests</a>
        </li>
        <li v-if="hasRole('TENANT')">
          <a href="/showProperties" class="nav-link box">See all Properties</a>
          <a href="/searchProperties" class="nav-link box">Search Properties</a>
        </li>
        <li v-if="hasRole('ADMIN')">
          <a href="/users" class="nav-link box">Users</a>
        </li>
        <li v-if="isAuthenticated">
          <a href="#" @click.prevent="handleLogout" class="nav-link box">Log Out</a>
        </li>
        <li v-if="!isAuthenticated">
          <a href="/login" class="nav-link box">Login</a>
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
  isHomePage.value = to.name === 'home'; // Check for styling if it's home page
  next();
});
</script>

<style scoped>

</style>
