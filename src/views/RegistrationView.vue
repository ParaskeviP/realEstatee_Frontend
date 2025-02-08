<template>
  <div class="overlay white-text">
    <div class="modal-container">
      <div class="modal-content">
        <div class="text-center mb-4">
          <h3 class="display-4 text-dark font-bold">User Registration</h3>
        </div>

        <form @submit.prevent="onFormSubmit">
          <div class="form-group">
            <label for="usernameFormControl" class="form-label text-dark">User Name</label>
            <input v-model="user.username" type="text" class="form-control rounded-3" id="usernameFormControl" />
          </div>

          <div class="form-group">
            <label for="emailFormControl" class="form-label text-dark">Email</label>
            <input v-model="user.email" type="text" class="form-control rounded-3" id="emailFormControl" />
          </div>

          <div class="form-group">
            <label for="passwordFormControl" class="form-label text-dark">Password</label>
            <input v-model="user.password" type="password" class="form-control rounded-3" id="passwordFormControl" />
          </div>

          <div class="form-group">
            <label for="userRoleFormControl" class="form-label text-dark">User Role</label>
            <select v-model="user.role" class="form-control rounded-3" id="userRoleFormControl">
              <option value="ROLE_TENANT">Tenant</option>
              <option value="ROLE_OWNER">Owner</option>
            </select>
          </div>

          <div class="form-group">
            <label for="firstNameFormControl" class="form-label text-dark">First Name</label>
            <input v-model="user.fname" type="text" class="form-control rounded-3" id="firstNameFormControl" />
          </div>

          <div class="form-group">
            <label for="lastNameFormControl" class="form-label text-dark">Last Name</label>
            <input v-model="user.lname" type="text" class="form-control rounded-3" id="lastNameFormControl" />
          </div>


          <div v-if="user.userRole === 'ROLE_OWNER'">
            <div class="form-group">
              <label for="phoneFormControl" class="form-label text-dark">Phone</label>
              <input v-model="user.phoneNumber" type="text" class="form-control rounded-3" id="phoneFormControl" />
            </div>
          </div>

          <div class="text-center">
            <button type="submit" class="btn btn-primary btn-lg rounded-3 font-bold">Sign Up</button>
          </div>
        </form>

        <div class="h6 text-danger text-center mt-3">{{ msg }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const user = ref({
  username: '',
  email: '',
  password: '',
  role: '',
  fname: '',
  lname: '',
  phoneNumber: ''
});

const msg = ref('');
const router = useRouter();

const onFormSubmit = () => {
  const endpoint = `http://localhost:8080/api/auth/signup`; // Use backendURL variable here
  const userData = {
    username: user.value.username,
    email: user.value.email,
    password: user.value.password,
    role: [user.value.role],
    fname: user.value.fname,
    lname: user.value.lname,
    phoneNumber: user.value.role === 'ROLE_OWNER' ? user.value.phoneNumber : null
  };


  fetch(endpoint, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userData),
  })
      .then((response) => {
        if (response.ok) {
          msg.value = 'User registered successfully! Redirecting to login...';

          setTimeout(() => {
            router.push("/login");
          }, 2000);
        } else {
          msg.value = 'Failed to register user. Please try again.';
          console.log(JSON.stringify(userData));
        }
      })
      .catch((error) => {
        console.error('Error during registration:', error);
        msg.value = 'An error occurred during registration. Please try again.';
      });
};

const togglerOwnerFields = () => {
  //Reset owner-specific fields when switching roles
  if (user.value.role !== 'ROLE_OWNER') {
    user.value.phoneNumber = '';
  }
};
</script>


<style scoped>

</style>
