public enum CompositeSortType {
    TITLE_DESC {
        @Override
        int compare(CompositeTask a, CompositeTask b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    },
    DATE_ASC {
        @Override
        int compare(CompositeTask a, CompositeTask b) {
            return a.getDate().compareTo(b.getDate());
        }
    },
    DATE_DESC {
        @Override
        int compare(CompositeTask a, CompositeTask b) {
            return b.getDate().compareTo(a.getDate());
        }
    },
    TITLE_ASC {
        @Override
        int compare(CompositeTask a, CompositeTask b) {
            return b.getTitle().compareTo(a.getTitle());
        }
    };

    abstract int compare(CompositeTask a, CompositeTask b);
}
