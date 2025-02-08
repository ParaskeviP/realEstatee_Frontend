<template>
  <div class="property-search">
    <h1>Search Properties</h1>
    <form @submit.prevent="searchProperties">
      <!-- Επιλογή Τύπου Αναζήτησης -->
      <div>
        <label for="searchType">Search by:</label>
        <select v-model="searchType" id="searchType" required>
          <option value="getById">ID</option>
          <option value="getByRoomNumber">Number of Rooms</option>
          <option value="getByBedNumber">Number of Bedrooms</option>
          <option value="getByBathNumber">Number of Bathrooms</option>
          <option value="getByCity">City</option>
          <option value="getByStreet">Street</option>
          <option value="getBySize">Size</option>
          <option value="getByPrice">Price</option>
          <option value="getByPetsAllowed">Pets Allowed</option>
          <option value="getByHasGarden">Has Garden</option>
          <option value="getByHasBalcony">Has Balcony</option>
          <option value="getByHasHeating">Has Heating</option>
          <option value="getByHasAC">Has AC</option>
          <option value="getByIsFurnished">Is Furnished</option>
          <option value="getByBuiltYear">Built Year</option>
          <option value="getByRenovYear">Renovation Year</option>
        </select>
      </div>

      <!-- Επιλογή Τιμής -->
      <label for="queryValue">Value:</label>

      <!-- Dropdown για Ναι/Όχι -->
      <select
          v-if="['getByPetsAllowed', 'getByHasGarden', 'getByHasBalcony', 'getByHasHeating', 'getByHasAC', 'getByIsFurnished'].includes(searchType)"
          v-model="queryValue" id="queryValue" required>
        <option :value="true">Yes</option>
        <option :value="false">No</option>
      </select>

      <!-- Input για πληκτρολόγηση -->
      <input
          v-else v-model="queryValue" type="text" id="queryValue" placeholder="Enter value" required/>

      <!-- Εμφάνιση Αποτελεσμάτων -->
      <button type="submit">Search</button>
    </form>

    <!-- Αποτελέσματα Αναζήτησης -->
    <div v-if="properties.length > 0">
      <h2>Search Results</h2>
      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>City</th>
          <th>Street</th>
          <th>Size</th>
          <th>Price</th>
          <th>Number of Rooms</th>
          <th>Bedrooms</th>
          <th>Bathrooms</th>
          <th>Pets Allowed</th>
          <th>Garden</th>
          <th>Balcony</th>
          <th>Heating</th>
          <th>AC</th>
          <th>Furnished</th>
          <th>Built Year</th>
          <th>Renovation Year</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="property in properties" :key="property.id">
          <td>{{ property.id }}</td>
          <td>{{ property.city }}</td>
          <td>{{ property.street }}</td>
          <td>{{ property.size }}</td>
          <td>{{ property.price }}</td>
          <td>{{ property.roomNum }}</td>
          <td>{{ property.bedNum }}</td>
          <td>{{ property.bathNum }}</td>
          <td>{{ property.petsAllowed }}</td>
          <td>{{ property.hasGarden }}</td>
          <td>{{ property.hasBalcony }}</td>
          <td>{{ property.hasHeating }}</td>
          <td>{{ property.hasAC }}</td>
          <td>
            <div v-if="property.furnished">Yes</div>
            <div v-else-if="!property.furnished">No</div>
          </td>
          <!-- <td>{{ property.isFurnished }}</td> -->
          <td>{{ property.builtYear }}</td>
          <td>{{ property.renovationYear }}</td>
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
      <h3>No Properties Available with this Search Criteria.</h3>
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
import { ref, onMounted, getCurrentInstance, computed} from 'vue';
import { useApplicationStore } from '@/stores/application.js';
const { loadUserData } = useApplicationStore();
const userData = loadUserData();
// Αρχικοποίηση των μεταβλητών
const searchType = ref("");
const queryValue = ref("");
const properties = ref([]);
const errorMessage = ref("");

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

// Μέθοδος για αναζήτηση
async function searchProperties() {
  try {
    hasSearched.value = false; // Αρχικοποίηση
    const response = await fetch(
        `http://localhost:8080/api/properties/${searchType.value}/${queryValue.value}`,

        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            'Authorization': `Bearer ${userData.token}`,},

        }
    );
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    hasSearched.value = true; // Σηματοδοτούμε ότι έγινε αναζήτηση

    const data = await response.json();
    properties.value = data;
    console.log(properties.value);
    errorMessage.value = ""; // Καθαρισμός μηνύματος σφάλματος
  } catch (error) {
    hasSearched.value = true; // Σηματοδοτούμε ότι έγινε αναζήτηση
    console.error("Error fetching properties:", error);
    errorMessage.value = "Failed to fetch properties. Please try again.";
  }
}

</script>

<style scoped>
.property-search {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.error {
  color: red;
  font-weight: bold;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 10px;
  border: 1px solid #ddd;
  text-align: left;
}

th {
  background-color: #f4f4f4;
}

button {
  margin-top: 10px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>

