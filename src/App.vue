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
  <div id="app" :class="{ home: $route.path === '/' }">
    <AppHeader />
    <main class="content">
      <RouterView />
    </main>
    <AppFooter />
  </div>
</template>

<style>
html, body {
  font-family: 'Roboto', 'Helvetica', sans-serif;
  line-height: 1.6;
  background: none;
  color: #333;
}

.content {
  flex: 1;
  width: 100%;
  box-sizing: border-box;
}

html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  flex: 1;
  box-sizing: border-box;
}

#app {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  min-height: 100vh;
  margin: 0;
  padding-top: 100px;
  
  background-attachment: fixed;
  background-size: cover;
  background-image: url('@/assets/background.png');
  /* background-image: url('https://raw.githubusercontent.com/ParaskeviP/DevOps/30da7a53c60660ff9cb6326bdfb99b8e02ac24b1/background.png?token=GHSAT0AAAAAADAG4O5AXVD4AMRDJI5X2ATAZ6P7LOA'); */
  background-position: center;
  /* background: linear-gradient(to bottom, #7b8ab7, #dadadb);  */
}

.home header {
  padding-top:100px;
}

footer {
  margin-top: auto;
  width: 100%;
  padding: 0px 0;
}

main {
  width: 80%;
  margin: 2rem 0;
}
</style>