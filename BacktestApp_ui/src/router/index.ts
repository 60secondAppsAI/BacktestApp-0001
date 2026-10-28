import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Backtests from '../components/Backtests.vue';
import BacktestDetail from '../components/BacktestDetail.vue';
import BacktestParameters from '../components/BacktestParameters.vue';
import BacktestParameterDetail from '../components/BacktestParameterDetail.vue';
import BacktestTrades from '../components/BacktestTrades.vue';
import BacktestTradeDetail from '../components/BacktestTradeDetail.vue';
import BacktestResults from '../components/BacktestResults.vue';
import BacktestResultDetail from '../components/BacktestResultDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/backtests',
    name: 'Backtests',
    component: Backtests
  },
  {
    path: '/backtest/:id',
    name: 'BacktestDetail',
    component: BacktestDetail,
    props: true
  },
  {
    path: '/backtestParameters',
    name: 'BacktestParameters',
    component: BacktestParameters
  },
  {
    path: '/backtestParameter/:id',
    name: 'BacktestParameterDetail',
    component: BacktestParameterDetail,
    props: true
  },
  {
    path: '/backtestTrades',
    name: 'BacktestTrades',
    component: BacktestTrades
  },
  {
    path: '/backtestTrade/:id',
    name: 'BacktestTradeDetail',
    component: BacktestTradeDetail,
    props: true
  },
  {
    path: '/backtestResults',
    name: 'BacktestResults',
    component: BacktestResults
  },
  {
    path: '/backtestResult/:id',
    name: 'BacktestResultDetail',
    component: BacktestResultDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
