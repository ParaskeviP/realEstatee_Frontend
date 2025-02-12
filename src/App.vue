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
body {
  font-family: 'Roboto', 'Helvetica', sans-serif;
  line-height: 1.6;
  background: none;
  color: #333;
}

html, body, #app {
  margin: 0;
  padding: 0;
  height: 100%;
  overflow-x: hidden;
}

#app {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  min-height: 1000vh;
  padding-top: 170px;

  background: linear-gradient(to bottom right, #9e7a68, #c5a48d);
}

main {
  max-width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

header {
  color: #333333; /* Dark text color for header */
  text-align: center;
  transition: 0.5s ease;
}

main {
  width: 80%;
  margin: 2rem 0;
}
</style>