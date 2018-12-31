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
module.exports = {
  data: function () {
    let ret = {
      userId: '123',
      totalNumber: 1,
      loggedInNumber: 0,
      visitorNumber: 1,
      cart: [{comId: 1, name: 'Gujian3', unitPrice: 99.0, number: 2},
        {comId: 2, name: 'Overcooked', unitPrice: 69.0, number: 4}],
      commodities: [{comId: 1, name: 'Gujian3', price: 99.0, comment: 'Hope of Chinese game'},
        {comId: 2, name: 'Overcooked', price: 69.0, comment: 'Best multi-player games this year'}]
    }
    this.axios.get('/home.data')
      .then(function (resp) {
        console.log('Success!')
        console.log(resp)
        ret = resp
      })
      .catch(function (err) {
        console.log(err)
      })
    return ret
  },
  computed: {
    totalPrice () {
      return this.cart.reduce((sum, commodity) => {
        return sum + commodity.number * commodity.unitPrice
      }, 0)
    }
  }
}
</script>

<style scoped>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #66ccff;
    margin-top: 5px;
  }
</style>
