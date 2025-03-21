<template>
  <div class="overlay white-text" ref="overlay" @click.self="closeModal">
    <div class="modal-container">
      <div class="modal-content">
        <span class="close" @click="closeModalAndGoBack">&times;</span>
        <div class="text-center mb-4">
          <h3 class="display-4 text-dark font-bold">Sign Up</h3>
        </div>
        
        <form @submit.prevent="onFormSubmit">
          <div class="form-group">
            <label for="firstNameFormControl" class="form-label text-dark">First Name</label>
            <input v-model="user.fname" type="text" class="form-control rounded-3" id="firstNameFormControl" />
          </div>
          
          <div class="form-group">
            <label for="lastNameFormControl" class="form-label text-dark">Last Name</label>
            <input v-model="user.lname" type="text" class="form-control rounded-3" id="lastNameFormControl" />
          </div>
          
          <div class="form-group">
            <label for="emailFormControl" class="form-label text-dark">Email</label>
            <input v-model="user.email" type="text" class="form-control rounded-3" id="emailFormControl" />
          </div>
          
          <div class="form-group">
            <label for="usernameFormControl" class="form-label text-dark">Username</label>
            <input v-model="user.username" type="text" class="form-control rounded-3" id="usernameFormControl" />
          </div>
          
          <div class="form-group">
            <label for="passwordFormControl" class="form-label text-dark">Password</label>
            <input v-model="user.password" type="password" class="form-control rounded-3" id="passwordFormControl" />
          </div>
          
          <div class="form-group">
            <label for="userRoleFormControl" class="form-label text-dark">Role</label>
            <select v-model="user.role" class="form-control rounded-3" id="userRoleFormControl">
              <option value="ROLE_TENANT">Tenant</option>
              <option value="ROLE_OWNER">Owner</option>
            </select>
          </div>
          
          <div class="form-group">
              <label for="phoneFormControl" class="form-label text-dark">Phone Number</label>
              <input v-model="user.phoneNumber" type="text" class="form-control rounded-3" id="phoneFormControl" />
          </div>
          
          <div class="text-center">
            <button type="submit" class="btn btn-primary btn-lg rounded-3 font-bold">Welcome!</button>
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

const showModal = ref(true);
const msg = ref('');
const router = useRouter();

const onFormSubmit = () => {
  const endpoint = `http://localhost:8080/api/auth/signup`; 
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
  if (user.value.role !== 'ROLE_OWNER') {
    user.value.phoneNumber = '';
  }
};

const closeModal = () => {
  showModal.value = false;
};

const goBack = () => {
  if (window.confirm("Είστε σίγουρος ότι θέλετε να επιστρέψετε;")) {
    window.history.back();
  }
};

const closeModalAndGoBack = () => {
  closeModal();
  setTimeout(() => {
    goBack();
  }, 300);
};
</script>


<style scoped>
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-container {
  background-color: #b88d72;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
  max-width: 500px;
  width: 100%;
  text-align: center;
  max-height: 80vh; 
  overflow-y: auto; 
}

.modal-container::-webkit-scrollbar {
  width: 10px;
}

.modal-container::-webkit-scrollbar-thumb {
  background-color: #815c45; /* Χρώμα μπάρας */
  border-radius: 5px;
}

.modal-container::-webkit-scrollbar-track {
  background-color: #f1f1f1; 
}

/* Heading and Text */
h3 {
  font-size: 2rem;
  color: #333;
  font-weight: 700;
  margin-bottom: 2rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

label {
  font-size: 1rem;
  color: #333;
  margin-bottom: 0.5rem;
  display: block;
}

input.form-control,
select.form-control {
  padding: 12px;
  border-radius: 5px;
  border: 1px solid #ccc;
  width: 80%;
  font-size: 1rem;
  margin-bottom: 1rem;
  transition: border-color 0.3s ease;
}

input.form-control:focus,
select.form-control:focus {
  border-color: #a86f72;
  outline: none;
}

button.btn {
  background-color: #815c45;
  color: white;
  padding: 15px 25px;
  font-size: 1.2rem;
  border-radius: 5px;
  cursor: pointer;
  align-content: center;
  width: 40%;
  transition: background-color 0.3s ease;
  border: none;
}

button.btnX {
  background-color: #80535A;
  color: white;
  padding: 5px 9px;
  font-size: 1.2rem;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
  align-content: right;
  width: 15%;
  transition: background-color 0.3s ease;
  border: none;
}

button.btn:hover {
  background-color: #5a3b28; 
}

.close:hover {
  color: red;
}

.text-danger {
  color: #38120e; 
  font-size: 1rem;
  margin-top: 1rem;
}

@media (max-width: 768px) {
  .modal-container {
    padding: 1.5rem;
    max-width: 90%;
  }

  h3 {
    font-size: 1.5rem; 
  }

  .form-group {
    margin-bottom: 1rem;
  }
}

</style>