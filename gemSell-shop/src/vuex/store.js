import Vue from 'vue';
import Vuex from 'vuex';
import { login, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
Vue.use(Vuex)
//创建一个 store
const store = new Vuex.Store({
  state: {
    cartDate: JSON.parse(window.localStorage.getItem('goodkey')) || {},
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
    // 传入的数据是一个对象 格式{goodId:商品id,goodNum:数量}
    addGoods(state, goodsInfo) {
      if (state.cartDate[goodsInfo.goodId] == undefined) {
        // state.cartDate[goodInfo.goodId] = goodInfo.goodNum;
        // 为了要让Vue检测到数据的改变同步修改页面显示 需要调用Vue.set方法
        Vue.set(state.cartDate, goodsInfo.goodId, goodsInfo.goodNum);
      } else {
        //传过来的id已存在则累加
        state.cartDate[goodsInfo.goodId] += goodsInfo.goodNum;
      }
    },
    //更新数量时同步购物车的数量
    updateGoodsNum(state, goodsInfo) {
      state.cartDate[goodsInfo.goodId] = goodsInfo.goodNum;
    },
    deleteGood(state, goodId) {
      //要调用Vue.delete方法
      Vue.delete(state.cartDate, goodId);
    }
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
      //临时num
      let num = 0;
      //遍历对象
      for (const key in state.cartDate) {
        num += state.cartDate[key];
      }
      return num;
    }
  },
  userId: state => {
    return state.currentUser.userId
  },
  token: state => {
    return state.token
  }
})

window.onbeforeunload = function() {
  window.localStorage.setItem('goodkey', JSON.stringify(store.state.cartDate));
}
//暴露出去
export default store;
