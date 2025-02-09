<template>
  <div class="overlay dark-text">
    <div class="modal-container">
      <div class="modal-content">
        <div class="text-center mb-4">
          <h1 class="fs-3">Login</h1>
        </div>

        <div class="mb-2" v-if="authenticationFailed">
          <div class="alert alert-danger" role="alert">
            Authentication failed!
          </div>
        </div>
        <div class="mb-2">
          <label for="usernameFormControl" class="form-label mb-1">User Name</label>
          <div class="col-sm-4">
            <input v-model="credentials.username" type="text" class="form-control" id="usernameFormControl" />
          </div>
        </div>
        <div class="mb-2">
          <label for="passwordFormControl" class="form-label mb-1">Password</label>
          <div class="col-sm-4">
            <input v-model="credentials.password" type="password" class="form-control" id="passwordFormControl" />
          </div>
        </div>
        <div>
          <button type="button" class="btn btn-primary btn-lg rounded-3 font-bold" @click.prevent="onFormSubmit">Sign In</button>
        </div>
        <div>
          Need an acount? <a href="/register">Sign up</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';

const router = useRouter();
const {loadUserData, setUserData, persistUserData, isAuthenticated } = useApplicationStore();

const loading = ref(false);
const credentials = ref({
  username: '',
  password: ''
});
const authenticationFailed = ref(false);


const onFormSubmit = () => {
  loading.value = true;
  authenticationFailed.value = false;

  fetch(`http://localhost:8080/api/auth/signin`, { // Use backendURL variable here
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(credentials.value)
  })
      .then((response) => {
        if (response.ok) {
          response.json().then((data) => {
            setUserData(data);
            persistUserData();
            router.push({ name: 'home' });
          });
        } else {
          authenticationFailed.value = true;
        }
      })
      .catch((err) => {
        console.warn(err);
        authenticationFailed.value = true;
      })
      .finally(() => {
        loading.value = false;
      });
};

onBeforeMount(() => {
  if (isAuthenticated.value === true) {
    router.replace({ name: 'home' });
  }
});
</script>

<style scoped>

</style>
