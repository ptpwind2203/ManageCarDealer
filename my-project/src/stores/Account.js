import {defineStore} from 'pinia'

export const useAccountStore = defineStore('Account', {
    state: () => ({
        account: null,
    }),

    getters: {
        isAuthenticated: (state) => !!state.account,
    
        isAdmin: (state) => state.account?.roleName === 'ADMIN',

        isStaff: (state) => state.account?.roleName === 'STAFF',

        canManageUsers: (state) => 
            state.account?.roleName === 'ADMIN' || 
            state.account?.roleName === 'STAFF'
    },

    actions: {
        login(account) {
            this.account = account;
        },
        logout() {
            this.account = null;
        },
    },
});
        
