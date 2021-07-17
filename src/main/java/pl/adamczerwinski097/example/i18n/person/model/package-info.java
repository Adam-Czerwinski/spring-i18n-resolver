@org.hibernate.annotations.GenericGenerator(name = "personsSequence", strategy = "enhanced-sequence", parameters = {
        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "persons_sequence"), })
package pl.adamczerwinski097.example.i18n.person.model;
