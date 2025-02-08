<script setup>
import { onBeforeMount } from 'vue';
import { RouterView } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
import AppHeader from '@/components/AppHeader.vue';
import AppFooter from '@/components/AppFooter.vue';

const { loadUserData } = useApplicationStore();

onBeforeMount(() => {
  loadUserData();
});
</script>

<template>
  <div id="app">
    <AppHeader />
    <RouterView />
  </div>
</template>

<style>
html,
body,
#app {
  margin: 0;
  padding: 0;
  height: 100%;
  transition: background-color 0.5s ease;
}

body {
  font-family: 'Roboto', 'Helvetica', sans-serif;
  line-height: 1.6;
  color: #333;
}

#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f0f0f0; /* Light background */
}

header {
  background-color: #ffffff; /* Light background for header */
  color: #333333; /* Dark text color for header */
  padding: 1rem;
  text-align: center;
  transition: background-color 0.5s ease;
}

nav {
  background-color: #f7f7f7; /* Light background for nav */
  color: #333333; /* Dark text color for nav */
  padding: 1rem;
  display: flex;
  justify-content: space-around;
  width: 100%;
  transition: background-color 0.5s ease;
}

nav a {
  text-decoration: none;
  color: #333333; /* Dark text color for nav links */
  font-weight: bold;
  transition: color 0.3s;
}

nav a:hover {
  color: #3498db; /* Blue color for hover effect */
}

main {
  width: 80%;
  margin: 2rem 0;
}

footer {
  background-color: #f7f7f7; /* Light background for footer */
  color: #333333; /* Dark text color for footer */
  padding: 1rem;
  text-align: center;
  transition: background-color 0.5s ease;
}
</style>
