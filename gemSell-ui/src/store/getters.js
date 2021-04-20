const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  userName: state => state.user.userName,
  name: state => state.user.name,
  role: state => state.user.role
}
export default getters
