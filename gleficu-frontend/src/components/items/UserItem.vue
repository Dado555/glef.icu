<template>
  <div>
    <router-link :to="`/user/${this.user.id}`">
      <img src="@/assets/images/avatar.jpg" alt=""/>

      <h3>{{ user.name }}</h3>
<!--      <p class="text-gray-400">-->
<!--        {{ knownFor }}-->
<!--      </p>-->
    </router-link>
  </div>
</template>

<script>
export default {
  name: "UserItem",
  props: {
    user: {
      required: true,
    },
  },
}
</script>

<style scoped>

</style>