<template>
  <div>
    <!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" /> -->

    <div v-if="paginatedProperties.length > 0">
      <table class="table">
        <thead>
        <tr>
          <th>ID</th>
          <th>City</th>
          <th>Size</th>
          <th>Price</th>
          <th>Rooms</th>
          <th>Beds</th>
          <th>Baths</th>
          <th>Pets Allowed</th>
          <th>Garden</th>
          <th>Balcony</th>
          <th>Heating</th>
          <th>Furnished</th>
          <th>Renovation Year</th>
          <th>Built Year</th>
          <th>Options</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="property in paginatedProperties" :key="property.id">
          <td>{{ property.id }}</td>
          <td>{{ property.city }}</td>
          <td>{{ property.size }}</td>
          <td>{{ property.price }}</td>
          <td>{{ property.roomNum }}</td>
          <td>{{ property.bedNum }}</td>
          <td>{{ property.bathNum }}</td>
          <td>
            <span v-if="property.petsAllowed">✔️</span>
            <span v-else>✘</span>
          </td>
          <td>
            <span v-if="property.hasGarden">✔️</span>
            <span v-else>✘</span>
          </td>
          <td>
            <span v-if="property.hasBalcony">✔️</span>
            <span v-else>✘</span>
          </td>
          <td>
            <span v-if="property.hasHeating">✔️</span>
            <span v-else>✘</span>
          </td>
          <td>
            <span v-if="property.isFurnished">✔️</span>
            <span v-else>✘</span>
          </td>
          <td>{{ property.renovationYear }}</td>
          <td>{{ property.builtYear }}</td>
          <button class="btn stylish-btn" @click="makeRentalRequest(property.id)">
            Make Rental Request
          </button>
          <button class="btn stylish-btn" @click="makeViewingRequest(property.id)">
            Make Viewing Request
          </button>

        </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button @click="prevPage" :disabled="currentPage === 1" class="pagination-btn">Previous</button>
        Page {{ currentPage }} of {{ totalPages }}
        <button @click="nextPage" :disabled="currentPage === totalPages" class="pagination-btn">Next</button>
      </div>
    </div>
    <div v-else-if="hasSearched">
      <h3>No Properties Available</h3>
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
import { ref, onMounted, getCurrentInstance, computed } from 'vue';
import { useApplicationStore } from '@/stores/application.js';
const { loadUserData } = useApplicationStore();
const userData = loadUserData();
const properties = ref([]);
const instance = getCurrentInstance();
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

const fetchProperties = () => {
  hasSearched.value = false; // Αρχικοποίηση

  fetch(`http://localhost:8080/api/tenant/showProperties`, {  //only properties without tenant
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.token}`,
    },
  })
      .then(response => response.json())
      .then(data => {
        properties.value = data;
        hasSearched.value = true; // Σηματοδοτούμε ότι έγινε αναζήτηση

      })
      .catch(error => console.error('Error fetching properties:', error));

};

// Κάλεσε την `fetchProperties` κατά την εκκίνηση
onMounted(() => {
  fetchProperties();
});


const itemsPerPage = 5;
const currentPage = ref(1);

const paginatedProperties = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return properties.value.slice(start, end);
});

const totalPages = computed(() => Math.ceil(properties.value.length / itemsPerPage));

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

const makeRentalRequest = (propertyId) => {
  fetch(`http://localhost:8080/api/tenant/makeRentalRequest/${propertyId}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.token}`,
    },
  })
      .then((response) => {
        if (response.ok) {
          openModal('Request successfully sumbitted!');
          fetchProperties();
        } else {
          response.json().then(data => {
            openModal(`Failed to submit the request: ${data.message || 'Unknown error'}`);
          });
        }
      })
      .catch((error) => {
        openModal(`Error: ${error}`);
      });
};

const makeViewingRequest = (propertyId) => {
  fetch(`http://localhost:8080/api/tenant/makeViewingRequest/${propertyId}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.token}`,
    },
  })
      .then((response) => {
        if (response.ok) {
          openModal('Request successfully sumbitted!');
          fetchProperties();
        } else {
          response.json().then(data => {
            openModal(`Failed to submit the request: ${data.message || 'Unknown error'}`);
          });
        }
      })
      .catch((error) => {
        openModal(`Error: ${error}`);
      });
};

</script>


<style>
.pos {
  margin-top: 20px;
}

.editBtn {
  margin-right: 5px;
}

.stylish-btn {
  padding: 12px 24px !important;
  border-radius: 25px;
  text-decoration: none;
  color: #333; /* Dark text color */
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  background-color: #f0f0f0; /* Light gray background */
  border: none;
  display: inline-block;
  overflow: hidden;
  position: relative;
}

.stylish-btn:hover {
  background-color: #ccc; /* Light gray background on hover */
  transform: scale(1.05);
}

.stylish-btn::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(45deg, #f0f0f0, #ccc); /* Gradient background */
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
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: #f0f0f0; /* Light gray background */
  color: #333; /* Dark text color */
  margin: 0 auto;
  padding: 20px;
  border-radius: 5px;
  border: 1px solid #333; /* Dark border */
  width: 60%;
  max-width: 400px;
  z-index: 1002;
}

.close {
  color: #333; /* Dark text color */
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: #666; /* Darker text color on hover */
  text-decoration: none;
}

.success {
  color: #333; /* Dark text color */
}

.pagination {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination-btn {
  background-color: #4f5054;
  color: #fff;
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
  background-color: #f0f0f0; /* Light gray background */
  border: none;
  color: #333; /* Dark text color */
  border: 1px solid #ccc; /* Light border */
}

th,
td {
  padding: 12px;
  text-align: left;
  border: 1px solid #ccc; /* Light border */
}

th {
  background-color: #f0f0f0; /* Light gray background */
  color: #333; /* Dark text color */
}

.form-label {
  color: #333; /* Dark text color */
}

.form-input {
  background-color: #f0f0f0; /* Light gray background */
  color: #333; /* Dark text color */
  border: 1px solid #333; /* Dark border */
  padding: 8px;
  margin-bottom: 10px;
  width: 100%;
  border-radius: 5px;
}

.form-input:focus {
  outline: none;
}

.form-btn {
  background-color: #4e565c;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.form-btn:hover {
  background-color: #2c3e50;
}

.form-dropdown {
  background-color: #f0f0f0; /* Light gray background */
  color: #333; /* Dark text color */
  border: 1px solid #333; /* Dark border */
  padding: 8px;
  margin-bottom: 10px;
  width: 100%;
  border-radius: 5px;
}
</style>

