<template>
    <div>
    <h1>My Applications</h1>
      <div v-if="paginatedRequests.length > 0">
        <table class="table">
          <thead>
          <tr>
            <th>Owner</th>
            <th>Phone Number</th>
            <th>Property ID</th>
            <th>Request Category</th>
            <th>Status</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="request in paginatedRequests" :key="request.id">
            <td>{{ request.owner.lname +' '+ request.owner.fname}}</td>
            <td>{{ request.owner.phonenumber }}</td>
            <td>{{ request.property.id }}</td>
            <td>
                <span v-if="request.isRentalRequest">Rental</span>
                <span v-else>Viewing</span>
            </td>
            <td>
                <span v-if="request.isViewingApproved">✔️</span>
                <span v-if="request.isViewingApproved ===false">✘</span>
                <span v-else-if="request.isViewingApproved===null">Pending</span>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="pagination">
          <button @click="prevPage" :disabled="currentPage === 1" class="pagination-btn"><=</button>
          Page {{ currentPage }}/{{ totalPages }}
          <button @click="nextPage" :disabled="currentPage === totalPages" class="pagination-btn">=></button>
        </div>
      </div>
      <div v-else>
        No applications found!
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
  
  const requests = ref([]);
  const itemsPerPage = 5;
  const currentPage = ref(1);
  const showModal = ref(false);
  const modalMessage = ref('');
  
  onMounted(() => {
    fetch(`http://localhost:8080/api/tenant/getMyRequests`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${userData.token}`,
      },
    })
        .then(response => {
          if (!response.ok) {
            throw new Error('Failed to fetch');
          }
          return response.json();
        })
        .then(data => {
          requests.value = data;
        })
        .catch(error => {
          console.error('Error fetching:', error);
        });
  });
  
//   const removeUser = (userId) => {
//     fetch(`http://localhost:8080/api/admin/declineUser/${userId}`, {
//       method: 'DELETE',
//       headers: {
//         'Content-Type': 'application/json',
//         'Authorization': `Bearer ${userData.token}`,
//       }
//     })
//         .then(response => {
//           if (response.ok) {
//             openModal('Removed successfully!');
//           } else {
//             throw new Error('Failed to remove');
//           }
//           return response.json();
//         })
//         .catch(error => {
//           console.error('Error removing user:', error);
//         });
//   };
  
  const paginatedRequests = computed(() => {
    const start = (currentPage.value - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    return requests.value.slice(start, end);
  });
  
  const totalPages = computed(() => Math.ceil(requests.value.length / itemsPerPage));
  
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
  
  .table {
    width: 70%;
    border-collapse: collapse;
    overflow: hidden;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    background-color: #f0f0f0;
    color: #333;
    border: 1px solid #ccc;
  }
  
  table {
    border-collapse: collapse;
    margin-top: 20px;
    background-color: #fff;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
    margin: auto;
  }
  
  th, td {
    padding: 0px 10px;
    text-align: center;
    font-size: 16px;
    color: #333;
  }
  
  th {
    background-color: #5a0b0b;
    color: white;
    font-weight: bold;
  }
  
  tr:nth-child(even) {
    background-color: #f9f9f9;
  }
  
  tr:hover {
    background-color: #f1f1f157;
    cursor: pointer;
    border-radius: 10px;
  }
  
  td {
    font-family: 'Arial', sans-serif;
  }
  
  table th,
  table td {
    transition: all 0.7s ease-in-out;
  }
  
  button {
    margin-top: 10px;
    background: #5a0b0b;
    color: white;
    font-size: 16px;
    padding: 13px;
    font-weight: bold;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.1s ease-in-out;
    box-shadow: 0 4px 8px rgba(0, 123, 255, 0.3);
  }
  
  button:hover {
    background: #9e1414;
    transform: translateY(-3px); 
    box-shadow: 0 6px 12px rgba(0, 86, 179, 0.4);
  }
  
  button:active {
    transform: translateY(1px);
    box-shadow: #5a0b0b;
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