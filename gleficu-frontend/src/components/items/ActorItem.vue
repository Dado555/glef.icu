<template>
  <div>
    <router-link :to="`/actor/${this.actor.id}`">
      <img :src="profileImage" alt="" />

      <h3>{{ actor.name }}</h3>
      <p class="text-gray-400">
        {{ knownFor }}
      </p>
    </router-link>
  </div>
</template>

<script>
export default {
  props: {
    actor: {
      required: true,
    },
  },
  computed: {
    profileImage() {
      return (
        "https://image.tmdb.org/t/p/w235_and_h235_face/" +
        this.actor.profile_path
      );
    },
    knownFor() {
      let knownStr = "";
      for (let i; i < this.actor.known_for.length - 1; i++) {
        knownStr += this.actor.known_for[i].title;
      }

      return knownStr;
    },
  },
};
</script>

<style></style>
