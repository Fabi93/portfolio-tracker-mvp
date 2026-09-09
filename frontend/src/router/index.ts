import { createRouter, createWebHistory } from 'vue-router'
import OverviewView from '../views/OverviewView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'overview', component: OverviewView },
    { path: '/positionen', name: 'positions', component: () => import('../views/PositionsView.vue') },
    { path: '/transaktionen', name: 'transactions', component: () => import('../views/TransactionsView.vue') },
    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
})

export default router
