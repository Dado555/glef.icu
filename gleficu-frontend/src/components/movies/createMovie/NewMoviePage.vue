<template>
  <div :class="{'flex space-x-4': variant === 'horizontal',}">
    <ul
        class="list-none p-1.5 rounded-lg text-center overflow-auto whitespace-nowrap"
        :class="{'flex items-center mb-6': variant === 'vertical',}">
      <li
          v-for="(tab, index) in tabList"
          :key="index"
          style="color: whitesmoke;"
          class="w-full px-4 py-1.5 rounded-lg"
          :class="{
          'bg-yellow-500 shadow-xl': index + 1 === activeTab,
          'text-white': index + 1 !== activeTab,
        }"
      >
        <label
            :for="`${_uid}${index}`"
            v-text="tab"
            class="cursor-pointer block"
        />
        <input
            :id="`${_uid}${index}`"
            type="radio"
            :name="`${_uid}-tab`"
            :value="index + 1"
            v-model="activeTab"
            class="hidden"
        />
      </li>
    </ul>
    <template>
<!--      <new-movie-input-info :key="1" v-if="activeTab === 1"/>-->
      <new-movie-input-title :key="2" v-if="activeTab === 2" ref="byTitle" @on-validate="onStepValidate"/>
    </template>
  </div>
</template>

<script>
// import NewMovieInputInfo from "@/components/movies/createMovie/NewMovieInputInfo";
import NewMovieInputTitle from "@/components/movies/createMovie/NewMovieInputTitle";

export default {
  name: "NewMoviePage",
  data() {
    return {
      finalModel: {},
      activeTab: 2,
      tabList: ["Find by title"], // "Insert links"
    };
  },
  components: {
    NewMovieInputTitle,
    // NewMovieInputInfo
  },
  props: {
    variant: {
      type: String,
      required: false,
      default: () => "vertical",
      validator: (value) => ["horizontal", "vertical"].includes(value),
    },
  },
  methods: {
    validate(ref) {
      return this.$refs[ref].validate();
    },
    onStepValidate(validated, model) {
      if (validated) {
        this.finalModel = { ...this.finalModel, ...model };
      }
    }
  }
};
</script>

<style>

</style>