import { createRouter, createWebHistory } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
        path: '/',
        name: 'root', // Άλλαξε το name για σαφήνεια
        // **ΑΛΛΑΓΗ:** Η αρχική διαδρομή γίνεται redirect με βάση την κατάσταση αυθεντικοποίησης
        redirect: to => {
          const applicationStore = useApplicationStore();
          // Ανακατεύθυνση στο /home αν authenticated, αλλιώς στο /login
          return applicationStore.isAuthenticated ? '/home' : '/login';
        }
        // Αφαιρέθηκε το component: () => import('../views/LoginView.vue')
      },
    {
      path: '/home',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/newProperty',
      name: 'property-new',
      component: () => import('../views/AddPropertyView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/getRequests',
      name: 'request-list',
      component: () => import('../views/RequestsListView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/search',
      name: 'properties-search',
      component: () => import('../views/SearchProperties.vue'),
      meta: { requiresAuth: true}
    },
    {
      path: '/users',
      name: 'users',
      component: () => import('../views/UsersView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/manageProperties',
      name: 'properties-list',
      component: () => import('../views/PropertyManagementView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/RegistrationView.vue')
    },
    {
      path: '/applicationManagement',
      name: 'application-management',
      component: () => import('../views/ApplicationManagementView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/notifications',
      name: 'notifications',
      component: () => import('../views/MyApplicationsView.vue'),
      meta: { requiresAuth:true }
    },
    {
      path: '/profile',
      name: 'profile',
      component: () => import('../views/MyProfileView.vue'),
      meta: { requiresAuth:true }
    },
    {
      path: '/oprofile',
      name: 'owner_profile',
      component: () => import('../views/MyOwnerProfileView.vue'),
      meta: { requiresAuth:true }
    }
  ]
});

router.beforeEach(async(to, from, next) => {
  const applicationStore = useApplicationStore();
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);
  
  console.log('Route:', to.path);
  console.log('Requires Auth:', requiresAuth);
  console.log('Is Authenticated:', applicationStore.isAuthenticated);
  
  // If user is authenticated and tries to access root or login page, redirect to home
  if (applicationStore.isAuthenticated && (to.path === '/' || to.path === '/login')) {
    console.log('User is authenticated. Redirecting to /home');
    next('/home');
    return;
  }
  
  // Check if the user is authenticated when required
  if (requiresAuth && !applicationStore.isAuthenticated) {
    console.log('User not authenticated. Redirecting to /login');
    next('/login');
  } else {
    console.log('User authenticated or route does not require auth. Proceeding.');
    // Load user data to ensure we have the latest user info
    if (applicationStore.isAuthenticated) {
      applicationStore.loadUserData();
    }
    next();
  }
});

export default router;