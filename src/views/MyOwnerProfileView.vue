<template>
    <div class="user-profile">
      <h1>My Profile</h1>
      <div class="form-container">
      <div class="modal-container">

        <div class="content">

        <div class="user-info">
            <span class="label">Username: </span> <span>{{ user?.user?.username || 'N/A' }}</span>
        </div>
        <div class="user-info">
            <span class="label">Email: </span> <span>{{ user?.email || 'N/A' }}</span>
        </div>
        <div class="user-info">
            <span class="label">First Name: </span> <span>{{ user?.fname || 'N/A' }}</span>
        </div>
        <div class="user-info">
            <span class="label">Last Name: </span> <span>{{ user?.lname || 'N/A' }}</span>
        </div>
        <div class="user-info">
            <span class="label">Activated Account: </span> 
            <span v-if="user?.user?.isApproved">✔️</span>
            <span v-else>✘</span>
        </div>
        <div class="user-info">
            <span class="label">Role: </span>
            <span v-if="user?.user?.roles?.some(role => role.name === 'ROLE_TENANT')">Tenant</span>
            <span v-else-if="user?.user?.roles?.some(role => role.name === 'ROLE_OWNER')">Owner</span>
            <span v-else-if="user?.user?.roles?.some(role => role.name === 'ROLE_ADMIN')">Administrator</span>
            <span v-else>No role</span>
        </div>
        <div>
            <button v-if="user?.id" class="btn stylish-btn" @click="removeUser(user.id)">Delete Account</button>
        </div>
    </div>
    </div>
    </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useApplicationStore } from '@/stores/application.js';

const { loadUserData } = useApplicationStore();
const userData = loadUserData();

const user = ref([]);
const itemsPerPage = 5;
const currentPage = ref(1);
const showModal = ref(false);
const modalMessage = ref('');

const backendURL = import.meta.env.VITE_BACKEND;

onMounted(() => {
  fetch(`${backendURL}/api/owner/getMyProfile`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.token}`,
    },
  })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch user');
        }
        return response.json();
      })
      .then(data => {
        user.value = data;
      })
      .catch(error => {
        console.error('Error fetching user:', error);
      });
});

const removeUser = (userId) => {
  fetch(`${backendURL}/api/admin/declineUser/${userId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.token}`,
    }
  })
      .then(response => {
        if (response.ok) {
          openModal('Removed successfully!');
        } else {
          throw new Error('Failed to remove');
        }
        return response.json();
      })
      .catch(error => {
        console.error('Error removing user:', error);
      });
};

const paginatedUsers = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return users.value.slice(start, end);
});

const totalPages = computed(() => Math.ceil(users.value.length / itemsPerPage));

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const openModal = (message) => {
  modalMessage.value = message;
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
  setTimeout(() => {
    location.reload();
  }, 500);
};
</script>

<style>
.user-profile {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.modal-content {
  background-color: #80535A;
  color: #fff;
  padding: 30px;
  border-radius: 8px;
  border: 1px solid #444;
  width: 90%;
  max-width: 600px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
  z-index: 1002;
  margin: auto;
}

.modal-container {
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
}

.form-container {
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
  padding: 2rem;
  text-align: left;
  background-color: #732a2a;
  border-radius: 8px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  margin-top: 30px;
}

.user-card {
  width: 100%;
  max-width: 400px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  border: 1px solid #ccc;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.user-info {
  display: flex;
  justify-content: space-between;
  font-size: 16px;
  color: #fffefe;
  padding: 5px 0;
  border-bottom: 1px solid #eee;
}

.label {
  font-weight: bold;
  color: #d6d6d6;
}

button {
  margin-top: 10px;
  background: #7c0b0b;
  color: white;
  font-size: 16px;
  padding: 10px;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.1s ease-in-out;
  width: 100%;
}

button:hover {
  background: #df585e;
  box-shadow: 0 0 12px #df585e;
}

h1 {
  font-size: 4rem;
  color: #5a0b0b; 
  margin: 20px 0;
  position: absolute;
  top: 90px; 
  left: 20px;
  border-bottom: 3px double #4a4a4a; /* Διπλή γραμμή σε σκούρο γκρι */
    padding-bottom: 0px; /* Δίνει λίγο χώρο πριν τη γραμμή */
    width: fit-content; /* Για να προσαρμόζεται στο μήκος του τίτλου */
}

h1::after {
    content: "";
    display: block;
    width: 100vw;
    /* border-bottom: 3px double #4a4a4a; */
    margin-top: 0px;
}
</style>