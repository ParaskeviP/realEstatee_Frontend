<template>
  <div>
    <div v-if="paginatedUsers.length > 0">
      <table class="table">
        <thead>
        <tr>
          <th>Username</th>
          <th>Email</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in paginatedUsers" :key="user.id">
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
          <td v-if="user.id">
            <button class="btn stylish-btn" @click="removeUser(user.id)">Remove User</button>
          </td>
        </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button @click="prevPage" :disabled="currentPage === 1" class="pagination-btn">Previous</button>
        Page {{ currentPage }} of {{ totalPages }}
        <button @click="nextPage" :disabled="currentPage === totalPages" class="pagination-btn">Next</button>
      </div>
    </div>
    <div v-else>
      Δεν υπάρχουν εγγεγραμμένοι χρήστες!
    </div>
    <div v-if="showModal" class="modal">
      <div class="modal-content success">
        <span class="close" @click="closeModal">&times;</span>
        <p>{{ modalMessage }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useApplicationStore } from '@/stores/application.js';

const { loadUserData } = useApplicationStore();
const userData = loadUserData();
const backendURL = import.meta.env.VITE_BACKEND;

const users = ref([]);
const itemsPerPage = 5;
const currentPage = ref(1);
const showModal = ref(false);
const modalMessage = ref('');

onMounted(() => {
  fetch(`${backendURL}/api/admin/getUsersFalse`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.accessToken}`,
    },
  })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch users');
        }
        return response.json();
      })
      .then(data => {
        users.value = data;
      })
      .catch(error => {
        console.error('Error fetching users:', error);
      });
});

const removeUser = (userId) => {
  fetch(`${backendURL}/api/admin/users/${userId}/remove`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.accessToken}`,
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
.pagination {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination-btn {
  background-color: #ffffff;
  color: #333333;
  padding: 8px 16px;
  cursor: pointer;
  border: none;
  border-radius: 5px;
  margin: 0 5px;
}

.table {
  width: 100%;
  border-collapse: collapse;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  background-color: #ffffff;
  border: none;
  color: #333333;
  border: 1px solid #000000;
}

th, td {
  padding: 12px;
  text-align: left;
  border: 1px solid #000000;
}

th {
  background-color: #ffffff;
  color: #333333;
}
.stylish-btn {
  padding: 12px 24px !important;
  border-radius: 25px;
  text-decoration: none;
  color: #333333;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  background-color: #ffffff;
  border: none;
  display: inline-block;
  overflow: hidden;
  position: relative;
}

.stylish-btn:hover {
  background-color: #e0e0e0;
  transform: scale(1.05);
}

.stylish-btn::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(45deg, #ffffff, #f0f0f0);
  z-index: -1;
  transition: transform 0.3s ease;
  transform: scaleX(0);
  transform-origin: right;
}

.stylish-btn:hover::before {
  transform: scaleX(1);
  transform-origin: left;
}
.modal {
  display: none;
  position: fixed;
  z-index: 1001;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(255, 255, 255, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: #ffffff;
  color: #333333;
  margin: 0 auto;
  padding: 20px;
  border-radius: 5px;
  border: 1px solid #333333;
  width: 60%;
  max-width: 400px;
  z-index: 1002;
}

.close {
  color: #333333;
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: #aaaaaa;
  text-decoration: none;
}

.success {
  color: #333333;
}
</style>