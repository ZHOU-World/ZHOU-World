export default{
  // 注意：localStorage不能在nuxt执行的asyncData方法中使用
  // 因为async执行是在nuxt服务器中执行的，没有浏览器，也就没有window对象
  // /window对象指的是窗口对象，包含地址栏，标签页，alert，control等
  // 根据Key获取值
  fetch(key) {
    return window.localStorage.getItem(key)
  },
  // 根据key保存值
  save(key, val) {
    window.localStorage.setItem(key, val)
  },
  // 根据key删除值
  remove(key) {
    window.localStorage.removeItem(key)
  }
}
