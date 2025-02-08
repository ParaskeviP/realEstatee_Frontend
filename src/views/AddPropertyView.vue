<template>
  <div class="overlay white-text box">
    <div class="form-container">
      <div class="modal-container">
        <div class="content">
          <div class="text-center mb-4">
            <h1 class="fs-3">Add Property</h1>
          </div>

          <form @submit.prevent="onFormSubmit">
            <div class="mb-2">
              <label for="cityFormControl" class="form-label mb-1">City</label>
              <div class="col-sm-8">
                <input v-model="property.city" type="text" class="form-control" id="cityFormControl" pattern="[A-Za-z]+" title="Please enter only letters" required />
              </div>
            </div>

            <div class="mb-2">
              <label for="streetFormControl" class="form-label mb-1">Street</label>
              <div class="col-sm-8">
                <input v-model="property.street" type="text" class="form-control" id="streetFormControl" pattern="[A-Za-z]+" title="Please enter only letters" required />
              </div>
            </div>

            <div class="mb-2">
              <label for="streetNumFormControl" class="form-label mb-1">Street Number</label>
              <div class="col-sm-4">
                <input v-model="property.streetNum" type="number" class="form-control" id="streetNumFormControl"  min="0" title="Please enter only numbers" required />
              </div>
            </div>

            <div class="mb-2">
              <label for="priceFormControl" class="form-label mb-1">Price</label>
              <div class="col-sm-4">
                <input v-model="property.price" type="number" class="form-control" id="priceFormControl"  min="0" title="Please enter only numbers" required />
              </div>
            </div>

            <div class="mb-2">
              <label for="sizeFormControl" class="form-label mb-1">Size</label>
              <div class="col-sm-4">
                <input v-model="property.size" type="number" class="form-control" id="sizeFormControl"  min="0" title="Please enter only numbers" required />
              </div>
            </div>

            <div class="mb-2">
              <label for="roomNumFormControl" class="form-label mb-1">Number of Rooms</label>
              <div class="col-sm-4">
                <input v-model="property.roomNum" type="number" class="form-control" id="roomNumFormControl"  min="0" title="Please enter only numbers" required />
              </div>
            </div>

            <div class="mb-2">
              <label for="bathNumFormControl" class="form-label mb-1">Number of Bathrooms</label>
              <div class="col-sm-4">
                <input v-model="property.bathNum" type="number" class="form-control" id="bathNumFormControl"  min="0" title="Please enter only numbers" required />
              </div>
            </div>

            <div class="mb-2">
              <label for="bedNumFormControl" class="form-label mb-1">Number of Bedrooms</label>
              <div class="col-sm-4">
                <input v-model="property.bedNum" type="number" class="form-control" id="bedNumFormControl"  min="0" title="Please enter only numbers" required />
              </div>
            </div>

            <div class="mb-2">
              <label for="isFurnishedFormControl" class="form-label mb-1">Furnished</label>
              <div class="col-sm-4">
                <select v-model="property.isFurnished" class="form-select" id="isFurnishedFormControl" required>
                  <option :value="true">Yes</option>
                  <option :value="false">No</option>
                </select>
              </div>
            </div>

            <div class="mb-2">
              <label for="hasParkingFormControl" class="form-label mb-1">Parking</label>
              <div class="col-sm-4">
                <select v-model="property.hasParking" class="form-select" id="hasParkingFormControl" required>
                  <option :value="true">Yes</option>
                  <option :value="false">No</option>
                </select>
              </div>
            </div>

            <div class="mb-2">
              <label for="hasBalconyFormControl" class="form-label mb-1">Balcony</label>
              <div class="col-sm-4">
                <select v-model="property.hasBalcony" class="form-select" id="hasBalconyFormControl" required>
                  <option :value="true">Yes</option>
                  <option :value="false">No</option>
                </select>
              </div>
            </div>

            <div class="mb-2">
              <label for="hasGardenFormControl" class="form-label mb-1">Garden</label>
              <div class="col-sm-4">
                <select v-model="property.hasGarden" class="form-select" id="hasGardenFormControl" required>
                  <option :value="true">Yes</option>
                  <option :value="false">No</option>
                </select>
              </div>
            </div>

            <div class="mb-2">
              <label for="hasHeatingFormControl" class="form-label mb-1">Heating</label>
              <div class="col-sm-4">
                <select v-model="property.hasHeating" class="form-select" id="hasHeatingFormControl" required>
                  <option :value="true">Yes</option>
                  <option :value="false">No</option>
                </select>
              </div>
            </div>

            <div class="mb-2">
              <label for="hasACFormControl" class="form-label mb-1">AC</label>
              <div class="col-sm-4">
                <select v-model="property.hasAC" class="form-select" id="hasACFormControl" required>
                  <option :value="true">Yes</option>
                  <option :value="false">No</option>
                </select>
              </div>
            </div>

            <div class="mb-2">
              <label for="petsAllowedFormControl" class="form-label mb-1">Pets Allowed</label>
              <div class="col-sm-4">
                <select v-model="property.petsAllowed" class="form-select" id="petsAllowedFormControl" required>
                  <option :value="true">Yes</option>
                  <option :value="false">No</option>
                </select>
              </div>
            </div>

            <div class="mb-2">
              <label for="builtYearFormControl" class="form-label mb-1">Built Year</label>
              <div class="col-sm-4">
                <input v-model="property.builtYear" type="number" class="form-control" id="builtYearFormControl"  min="1950" max="2025" title="Please enter only numbers" required />
              </div>
            </div>

            <div class="mb-2">
              <label for="renovationYearFormControl" class="form-label mb-1">Renovation Year</label>
              <div class="col-sm-4">
                <input v-model="property.renovationYear" type="number" class="form-control" id="renovationYearFormControl"  min="1950" max="2025" title="Please enter only numbers" required />
              </div>
            </div>

            <button type="submit" class="btn btn-primary btn-lg rounded-3 font-bold">Submit</button>
          </form>

          <div v-if="showModal" class="modal">
            <div class="modal-content success">
              <span class="close" @click="closeModal">&times;</span>
              <p>{{ modalMessage }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useApplicationStore } from '@/stores/application.js';

const { loadUserData } = useApplicationStore();
const userData = loadUserData();

const property = ref({
  city : '',
  street: '',
  streetNum: '',
  price: '',
  size: '',
  roomNum: '',
  bathNum: '',
  bedNum: '',
  isFurnished: '',
  hasParking: '',
  hasBalcony: '',
  hasGarden: '',
  hasHeating: '',
  hasAC: '',
  petsAllowed: '',
  builtYear: '',
  renovationYear: ''
});
const showModal = ref(false);
const modalMessage = ref('');
const openModal = (message) => {
  modalMessage.value = message;
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};

const onFormSubmit = () => {
  fetch('http://localhost:8080/api/owner/newProperty', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userData.token}`,
    },
    body: JSON.stringify(property.value),
  })
      .then((response) => {
        if (response.ok) {
          openModal('Property saved successfully!'); //if response is ok display saved
          clearForm();
        } else {
          openModal('Failed to save property.');//response not ok show failed message
        }
      })
      .catch((error) => {
        console.log = `Error: ${error}`;
      });
};

const clearForm = () => {
  property.value = {
    city : '',
    street: '',
    streetNum: '',
    price: '',
    size: '',
    roomNum: '',
    bathNum: '',
    bedNum: '',
    isFurnished: '',
    hasParking: '',
    hasBalcony: '',
    hasGarden: '',
    hasHeating: '',
    hasAC: '',
    petsAllowed: '',
    builtYear: '',
    renovationYear: ''
  };

};
</script>

<style scoped>
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
  background-color: #333332;
  color: #fff;
  margin: 0 auto;
  padding: 20px;
  border-radius: 5px;
  border: 1px solid #fff;
  width: 60%;
  max-width: 400px;
  z-index: 1002;
}

.close {
  color: #fff;
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: #aaa;
  text-decoration: none;
}

.success {
  color: #b9bdb9;
}
body, html {
  margin: 0;
}
.modal-container {
  width: 150%;
  max-width: 1000px;
  margin: 0 auto;
  align-items: center;

}

.content {
  padding: 20px;
  border-radius: 8px;
  background-color: #2d2c2c;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);

}
.overlay {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  background-color: transparent;
}

.form-container {
  height: 100%;
  width: 100%;
  max-width: 500px;
  text-align: center;
}

.mb-2 {
  width: 100%;
}

.form-control {
  width: 100%;
  padding: 2px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 16px;
}

.btn {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border-radius: 8px;
  background: linear-gradient(to right, #646665, #2e3030, #2a2828);
  color: #ffffff;
  transition: background 0.3s ease;
  border: none;
  outline: none;
  margin-top: 20px;
}

.btn:hover {
  box-shadow: 0 0 10px rgba(80, 173, 240, 0.5);
}

.popup {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 10px;
  border-radius: 8px;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  z-index: 1000;
}

.success-popup {
  color: green;
  border: 2px solid green;
}

.failure-popup {
  color: red;
  border: 2px solid red;
}

.icon {
  margin-right: 5px;
}
.white-text {
  color: #fff;
}
</style>