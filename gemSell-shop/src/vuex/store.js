import Vue from 'vue';
import Vuex from 'vuex';
import { login, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
Vue.use(Vuex)
//创建一个 store
const getDefaultState = ()=>{
  return {
    cartDate: 0,
    pathFrom: '',
    currentUser: '',
    token: getToken()
  }
}
const store = new Vuex.Store({
  state: {
    cartDate: 0,
    pathFrom: '',
    currentUser: '',
    token: getToken()
  },
  mutations: {
    RESET_STATE: (state) => {
      Object.assign(state, getDefaultState())
    },
    setCurrentUser:(state,data)=>{
      state.currentUser = data
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    //更新数量时同步购物车的数量
    updateGoodsNum(state, num) {
      state.cartDate = num;
    },
  },
  actions: {
    // user login
    login({ commit }, userInfo) {
      const { userName, password } = userInfo
      return new Promise((resolve, reject) => {
        login({ userName: userName.trim(), password: password }).then(response => {
          const { data } = response
          commit('SET_TOKEN', data)
          setToken(data)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // get user info
    getInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          const { data } = response
          console.log(data)
          if (!data) {
            return reject('Verification failed, please Login again.')
          }
          commit('setCurrentUser', { ...data })
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // user logout
    logout({ commit, state }) {
      return new Promise((resolve, reject) => {
        removeToken() // must remove  token  first
        commit('RESET_STATE')
        resolve()
      })
    },

    // remove token
    resetToken({ commit }) {
      return new Promise(resolve => {
        removeToken() // must remove  token  first
        commit('RESET_STATE')
        resolve()
      })
    }
  },
  getters: {
    goodsCount: state => {
      return state.cartDate
    }
  },
  userId: state => {
    return state.currentUser.userId
  },
  token: state => {
    return state.token
  }
})

//暴露出去
export default store;
