<template>
  <div class="dashboard">
    <h2>Dashboard</h2>
    <button @click="logout">Logout</button>
    <h3>Available Programs</h3>
    <table v-if="groupedMenuItems.length" class="program-table">
      <thead>
      <tr>
        <th>Program Name</th>
        <th>Code</th>
        <th>Input</th>
        <th>Update</th>
        <th>Delete</th>
        <th>Query</th>
        <th>Print</th>
      </tr>
      </thead>
      <tbody>
      <template v-for="(group, groupIndex) in groupedMenuItems" :key="groupIndex">
        <!-- Dropdown for null or dashed programCode -->
        <tr v-if="group.isDropdown" :class="{ 'even-row': groupIndex % 2 === 0, 'odd-row': groupIndex % 2 !== 0 }">
          <td colspan="7">
            <details>
              <summary>
                <span class="program-name">{{ group.header.programName }}</span>
              </summary>
              <table class="nested-table">
                <tbody>
                <tr v-for="(item, itemIndex) in group.items" :key="item.programId" :class="{ 'even-row': itemIndex % 2 === 0, 'odd-row': itemIndex % 2 !== 0 }">
                  <td class="program-name">{{ item.programName }}</td>
                  <td>{{ item.programCode }}</td>
                  <td>{{ item.authorityInput }}</td>
                  <td>{{ item.authorityUpdate }}</td>
                  <td>{{ item.authorityDelete }}</td>
                  <td>{{ item.authorityQuery }}</td>
                  <td>{{ item.authorityPrint }}</td>
                </tr>
                </tbody>
              </table>
            </details>
          </td>
        </tr>
        <!-- Regular row for non-dropdown items -->
        <tr v-else :class="{ 'even-row': groupIndex % 2 === 0, 'odd-row': groupIndex % 2 !== 0 }">
          <td class="program-name">{{ group.header.programName }}</td>
          <td>{{ group.header.programCode }}</td>
          <td>{{ group.header.authorityInput }}</td>
          <td>{{ group.header.authorityUpdate }}</td>
          <td>{{ group.header.authorityDelete }}</td>
          <td>{{ group.header.authorityQuery }}</td>
          <td>{{ group.header.authorityPrint }}</td>
        </tr>
      </template>
      </tbody>
    </table>
    <p v-else>Loading...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';

const menuItems = ref([]);
const groupedMenuItems = ref([]);
const router = useRouter();
const authStore = useAuthStore();

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:9090/api/main', {
      headers: {
        Authorization: authStore.token,
      },
    });
    menuItems.value = response.data;
    groupMenuItems();
  } catch (err) {
    console.error('Failed to fetch menu items:', err);
    router.push('/');
  }
});

const groupMenuItems = () => {
  const result = [];
  let currentGroup = null;

  menuItems.value.forEach((item) => {
    const isDropdown = !item.programCode || /^-+$/.test(item.programCode); // Null or all dashes
    if (isDropdown) {
      if (currentGroup) {
        result.push(currentGroup); // Push previous group if exists
      }
      currentGroup = { isDropdown: true, header: item, items: [] };
    } else if (currentGroup) {
      currentGroup.items.push(item); // Add to current dropdown group
    } else {
      result.push({ isDropdown: false, header: item }); // Standalone row
    }
  });

  if (currentGroup) {
    result.push(currentGroup); // Push final group
  }

  groupedMenuItems.value = result;
};

const logout = () => {
  authStore.clearToken();
  router.push('/');
};
</script>

<style scoped>
.dashboard {
  max-width: 800px;
  margin: 50px auto;
  padding: 20px;
}

button {
  padding: 10px 20px;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #c82333;
}

.program-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.program-table th,
.program-table td {
  padding: 12px;
  text-align: center;
  border: 1px solid #ddd;
}

.program-table th {
  background-color: #f4f4f4;
  font-weight: bold;
}

.program-table .program-name {
  background-color: #e3f2fd;
  font-weight: 600;
  text-align: left;
}

.even-row {
  background-color: #ffffff;
}

.odd-row {
  background-color: #f9f9f9;
}

.program-table tr:hover {
  background-color: #f1f1f1;
}

details {
  width: 100%;
}

summary {
  padding: 12px;
  cursor: pointer;
  background-color: #e0e0e0; /* Light gray for dropdown header */
  border: 1px solid #ddd;
}

.nested-table {
  width: 100%;
  border-collapse: collapse;
  margin: 0;
}

.nested-table td {
  padding: 10px;
  border: 1px solid #ddd;
}
</style>