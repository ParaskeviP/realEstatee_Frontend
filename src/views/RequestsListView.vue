<template>
  <div class="request-list-view white-text">
    <div v-if="requests && requests.length > 0">
      <table class="table">
        <thead>
        <tr>
          <th>Tenant ID</th>
          <th>Property ID</th>
          <th>Type of Request</th>
          <th>Status</th>
          <th>Options</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="request in paginatedRequests" :key="request.id">
          <td>{{ request.tenant.id }}</td>
          <td>{{ request.property.id }}</td>
          <td>{{ request.isRentalRequest ? "Rental Request" : "Viewing Request" }}</td>
          <td>
      <span v-if="!request.isRentalRequest">
        {{ request.isViewingApproved === null ? "No Status" : request.isViewingApproved ? "Approved" : "Declined" }}
      </span>
          </td>

          <td>
            <!-- Show buttons only if the request is a rental request or if the status is null for viewing requests -->
            <button v-if=" (request.isViewingApproved ===null && !request.isViewingApproved === true && !request.isViewingApproved === false) || request.isRentalRequest ||( !request.isRentalRequest && request.isViewingApproved === null)" class="btn stylish-btn" @click="approveRequest(request.id, request.isRentalRequest)">
              Approve Request
            </button>

            <button v-if=" (request.isViewingApproved ===null && !request.isViewingApproved === true && !request.isViewingApproved === false) || request.isRentalRequest ||( !request.isRentalRequest && request.isViewingApproved === null)" class="btn stylish-btn" @click="declineRequest(request.id)">
              Decline Request
            </button>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="pagination white-text"> <!-- buttons for previous and next pages in the table -->
        <button @click="prevPage" :disabled="currentPage === 1">Previous</button>
        Page {{ currentPage }} of {{ totalPages }}
        <button @click="nextPage" :disabled="currentPage === totalPages">Next</button>
      </div>
    </div>
    <div v-else-if="hasSearched">
      <h3>No Requests</h3>
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
import { useRouter } from 'vue-router';

const { loadUserData } = useApplicationStore();
const userData = loadUserData();
const router = useRouter();

const requests = ref([]);
const itemsPerPage = 5;
const currentPage = ref(1);
const showModal = ref(false);
const modalMessage = ref('');
const hasSearched = ref(false); // Μεταβλητή για να ελέγχουμε αν έγινε αναζήτηση

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

onMounted(() => {
  fetchRequests();
});

const fetchRequests = () => {
  hasSearched.value = false; // Αρχικοποίηση
  fetch(`http://localhost:8080/api/owner/getRequests`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.token}`,
    },
  })
      .then(response => response.json())
      .then(data => {
        requests.value = data;
        hasSearched.value = true; // Σηματοδοτούμε ότι έγινε αναζήτηση
        console.log(requests.value);
      })
      .catch(error => console.error('Error fetching requests:', error));
};

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

const approveRequest = (requestId, isRentalRequest) => {
  if (isRentalRequest) {
    approveRentalRequest(requestId);
  } else {
    approveViewingRequest(requestId);
  }
};

const approveViewingRequest = (requestId) => {
  fetch(`http://localhost:8080/api/owner/approveViewingRequest/${requestId}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.token}`,
    },
  })
      .then((response) => {
        if (response.ok) {
          openModal('Request approved successfully!');
          fetchRequests();
        } else {
          response.json().then(data => {
            openModal(`Failed to approve the request: ${data.message || 'Unknown error'}`);
          });
        }
      })
      .catch((error) => {
        openModal(`Error: ${error}`);
      });
};


const approveRentalRequest = (requestId) => {
  fetch(`http://localhost:8080/api/owner/approveRentalRequest/${requestid}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.token}`,
    },
  })
      .then((response) => {
        if (response.ok) {
          openModal('Request approved successfully!');
          fetchRequests();
        } else {
          openModal('Failed to approve the request.');
        }
      })
      .catch((error) => {
        openModal(`Error: ${error}`);
      });
};

const declineRequest = (requestId) => {
  fetch(`http://localhost:8080/api/owner/deleteRequest/${requestId}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.token}`,
    },
  })
      .then((response) => {
        if (response.ok) {
          openModal('Request declined successfully!');
          fetchRequests();
        } else {
          openModal('Failed to decline the request.');
        }
      })
      .catch((error) => {
        openModal(`Error: ${error}`);
      });
};
</script>


<style scoped>
/*  */
.pagination {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination button {
  background-color: #3498db; /* Blue */
  color: #fff; /* White */
  padding: 8px 16px;
  cursor: pointer;
  border: none;
}

.request-list-view {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.table {
  width: 100%;
  border-collapse: collapse;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  background-color: #f0f0f0; /* Light gray */
  border: none;
  border: 1px solid #ddd; /* Light gray border */
  margin-top: 10px;
}

th, td {
  padding: 12px;
  text-align: left;
  border: 1px solid #ddd; /* Light gray border */
}

th {
  background-color: #3498db; /* Blue */
  color: #fff; /* White */
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
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: #f0f0f0; /* Light gray */
  color: #333; /* Dark gray */
  margin: 0 auto;
  padding: 20px;
  border-radius: 5px;
  border: 1px solid #333; /* Dark gray border */
  width: 60%;
  max-width: 400px;
  z-index: 1002;
}

.close {
  color: #333; /* Dark gray */
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: #aaa; /* Lighter gray on hover/focus */
  text-decoration: none;
}

.success {
  color: #2ecc71; /* Green */
}
.btn {
  margin-right: 5px;
  padding: 10px 20px !important;
  border-radius: 5px;
  text-decoration: none;
  color: #fff; /* White */
  cursor: pointer;
  transition: background-color 0.3s ease;
  background-color: #3498db; /* Blue */
  border: none;
  display: inline-block;
}

.btn:hover {
  background-color: #2980b9; /* Darker blue on hover */
}

.stylish-btn {
  padding: 12px 24px !important;
  border-radius: 25px;
  text-decoration: none;
  color: #fff; /* White */
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  background-color: #3498db; /* Blue */
  border: none;
  display: inline-block;
  overflow: hidden;
  position: relative;
}

.stylish-btn:hover {
  background-color: #2980b9; /* Darker blue on hover */
  transform: scale(1.05);
}

.stylish-btn::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(45deg, #3498db, #2980b9); /* Gradient blue */
  z-index: -1;
  transition: transform 0.3s ease;
  transform: scaleX(0);
  transform-origin: right;
}

.stylish-btn:hover::before {
  transform: scaleX(1);
  transform-origin: left;
}

h3 {
  margin-top: 20px;
  color: #333; /* Dark gray */
}

.white-text {
  color: #333; /* Dark gray */
}
</style>

