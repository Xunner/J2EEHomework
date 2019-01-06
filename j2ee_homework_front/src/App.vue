<template>
  <div id="app">
    <label>在线总人数：{{totalNumber}}，已登录用户数：{{loggedInNumber}}，游客数：{{visitorNumber}}</label>
    <form method="get" action="logout">
      <table border="0" align="left">
        <tr>
          <td>Hello</td>
          <td>{{userId}}</td>
          <td><input type="submit" value="Log out"></td>
        </tr>
      </table>
    </form>
    <br>
    <br>
    <div style="float: left;">
      <form method="post" action="add-to-cart">
        <table border="1" align=left style="margin-right: 50px; margin-bottom: 30px">
          <tr>
            <td colspan="4" align="center">List of Commodities</td>
          </tr>
          <tr>
            <td>name</td>
            <td>price (￥)</td>
            <td>description</td>
            <td>number to buy</td>
          </tr>
          <tr v-for="commodity in commodities" :key="commodity.comId">
            <td>{{commodity.name}}</td>
            <td align="right">{{commodity.price}}</td>
            <td>{{commodity.comment}}</td>
            <td><label><input type="number" :name="commodity.comId" value=0></label></td>
          </tr>
          <tr>
            <td colspan="4" align="center">
              <input type="submit" value="Add to cart">
              <input type="reset" value="Reset">
            </td>
          </tr>
        </table>
      </form>
      <form method="post" action="pay">
        <table border="1" align=left>
          <tr>
            <td colspan="4" align="center">Cart</td>
          </tr>
          <tr>
            <td>name</td>
            <td>unit price (￥)</td>
            <td>number</td>
            <td>total (￥)</td>
          </tr>
          <tr v-for="commodity in cart" :key="commodity.comId">
            <td>{{commodity.name}}</td>
            <td align="right">{{commodity.unitPrice}}</td>
            <td align="right">{{commodity.number}}</td>
            <td align="right">{{commodity.number * commodity.unitPrice}}</td>
          </tr>
          <tr>
            <td colspan="4" align="right">Total price: ￥{{totalPrice}}</td>
          </tr>
          <tr>
            <td colspan="4" align="center">
              <input type="submit" value="Pay">
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      userId: '123',
      totalNumber: 0,
      loggedInNumber: 0,
      visitorNumber: 0,
      cart: [{comId: 1, name: '古剑3', unitPrice: 99.0, number: 2},
        {comId: 2, name: 'Overcooked', unitPrice: 69.0, number: 4}],
      commodities: [{comId: 1, name: '古剑3', price: 99.0, comment: 'Hope of Chinese game'},
        {comId: 2, name: 'Overcooked', price: 69.0, comment: 'Best multi-player games this year'}]
    }
  },
  computed: {
    totalPrice () {
      return this.cart.reduce((sum, commodity) => {
        return sum + commodity.number * commodity.unitPrice
      }, 0)
    }
  },
  created () {
    let self = this
    this.axios.get('/home.ajax')
      .then(function (resp) {
        // console.log('Success!')
        let data = resp.data
        // console.log(data)
        // console.log(self.totalNumber)
        self.userId = data.userId
        self.totalNumber = data.totalNumber
        self.loggedInNumber = data.loggedInNumber
        self.visitorNumber = data.visitorNumber
        self.cart = data.cart
        self.commodities = data.commodities
        // console.log(self.totalNumber)
      })
      .catch(function (err) {
        console.log(err)
      })
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /*margin-top: 60px;*/
}
</style>
